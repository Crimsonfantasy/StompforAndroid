package jms.stomp.tool;

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;

import jms.stomp.StompConnectionFactory;
import jms.stomp.Istomp.Connection;
import jms.stomp.Istomp.Destination;
import jms.stomp.Istomp.Message;
import jms.stomp.Istomp.MessageConsumer;
import jms.stomp.Istomp.MessageListener;
import jms.stomp.Istomp.Session;
import jms.stomp.Istomp.Topic;

/**
 * A simple tool for consuming messages
 * 
 * 
 */
public class ConsumerTool extends Thread implements MessageListener {

	private boolean running;

	private Session session;
	private Destination destination;
	Connection connection;
	MessageConsumer consumer = null;
	// private MessageProducer replyProducer;

	private boolean pauseBeforeShutdown = false;
	/**
	 * using or containing an excess of words, so as to be pedantic or boring.
	 * ignore message;
	 */
	private boolean verbose = false;
	private int maxiumMessages;
	private static int parallelThreads = 1;
	private String subject = "TOOL.DEFAULT";

	/**
	 * topic: get topic message when value is true; get queue message when value
	 * is false;
	 */

	private boolean topic = true;
	private String user = null;
	private String password = null;
	private String url = null;
	private boolean transacted;
	private boolean durable;
	private String clientId;
	private int ackMode = Session.AUTO_ACKNOWLEDGE;
	private String consumerName = "James";
	private long receiveTimeOut;
	private long sleepTime = 0;
	private long batch = 10; // Default batch size for CLIENT_ACKNOWLEDGEMENT or
								// SESSION_TRANSACTED

	public void run() {
		running = true;

		StompConnectionFactory connectionFactory = new StompConnectionFactory(
				user, password, url);
		connection = connectionFactory.createConnection();
		if (durable && clientId != null && clientId.length() > 0
				&& !"null".equals(clientId)) {
			connection.setClientID(clientId);
		}

		connection.start();

		session = connection.createSession(transacted, ackMode);
		if (topic) {
			destination = session.createTopic(subject);
		} else {
			destination = session.createQueue(subject);
		}

		// /replyProducer = session.createProducer(null);
		// /replyProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

		if (durable && topic) {

			consumer = session.createDurableSubscriber((Topic) destination,
					consumerName);
		} else {
			consumer = session.createConsumer(destination);
		}

		if (maxiumMessages > 0) {
			try {
				consumeMessagesAndClose(connection, session, consumer);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			if (receiveTimeOut == 0) {
				consumer.setMessageListener(this);
			} else {
				try {
					consumeMessagesAndClose(connection, session, consumer,
							receiveTimeOut);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	synchronized boolean isRunning() {
		return running;
	}

	protected void consumeMessagesAndClose(Connection connection,
			Session session, MessageConsumer consumer) throws IOException {
		System.out.println("[" + this.getName()
				+ "] We are about to wait until we consume: " + maxiumMessages
				+ " message(s) then we will shutdown");

		for (int i = 0; i < maxiumMessages && isRunning();) {
			Message message = consumer.receive(1000);
			if (message != null) {
				i++;
				onMessage(message);
			}
		}
		System.out.println("[" + this.getName() + "] Closing connection");

		this.close();
		if (pauseBeforeShutdown) {
			System.out.println("[" + this.getName()
					+ "] Press return to shut down");
			System.in.read();
		}
	}

	protected void consumeMessagesAndClose(Connection connection,
			Session session, MessageConsumer consumer, long timeout)
			throws IOException {
		System.out
				.println("["
						+ this.getName()
						+ "] We will consume messages while they continue to be delivered within: "
						+ timeout + " ms, and then we will shutdown");

		Message message;
		while ((message = consumer.receive(timeout)) != null) {
			onMessage(message);
		}

		System.out.println("[" + this.getName() + "] Closing connection");

		this.close();

		if (pauseBeforeShutdown) {
			System.out.println("[" + this.getName()
					+ "] Press return to shut down");
			System.in.read();
		}
	}

	protected void close() {

		consumer.close();
		session.close();
		connection.close();
	}

	public void setAckMode(String ackMode) {
		if ("CLIENT_ACKNOWLEDGE".equals(ackMode)) {
			this.ackMode = Session.CLIENT_ACKNOWLEDGE;
		}
		if ("AUTO_ACKNOWLEDGE".equals(ackMode)) {
			this.ackMode = Session.AUTO_ACKNOWLEDGE;
		}
		if ("DUPS_OK_ACKNOWLEDGE".equals(ackMode)) {
			this.ackMode = Session.DUPS_OK_ACKNOWLEDGE;
		}
		if ("SESSION_TRANSACTED".equals(ackMode)) {
			this.ackMode = Session.SESSION_TRANSACTED;
		}
	}

	public void setClientId(String clientID) {
		this.clientId = clientID;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public void setDurable(boolean durable) {
		this.durable = durable;
	}

	public void setMaxiumMessages(int maxiumMessages) {
		this.maxiumMessages = maxiumMessages;
	}

	public void setPauseBeforeShutdown(boolean pauseBeforeShutdown) {
		this.pauseBeforeShutdown = pauseBeforeShutdown;
	}

	public void setPassword(String pwd) {
		this.password = pwd;
	}

	public void setReceiveTimeOut(long receiveTimeOut) {
		this.receiveTimeOut = receiveTimeOut;
	}

	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}

	public void setParallelThreads(int parallelThreads) {
		if (parallelThreads < 1) {
			parallelThreads = 1;
		}
		this.parallelThreads = parallelThreads;
	}

	public void setTopic(String topicName) {

		this.topic = true;
		this.subject = topicName;
	}

	public void setQueue(String queueName) {

		this.topic = !topic;
		this.subject = queueName;

	}

	public void setTransacted(boolean transacted) {
		this.transacted = transacted;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	public void setBatch(long batch) {
		this.batch = batch;
	}

	@Override
	public void onMessage(Message message) {

		if (this.verbose)
			return;

	}
}
