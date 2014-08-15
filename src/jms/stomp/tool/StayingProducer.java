package jms.stomp.tool;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import jms.stomp.StompConnectionFactory;
import jms.stomp.Istomp.Connection;
import jms.stomp.Istomp.DeliveryMode;
import jms.stomp.Istomp.Destination;
import jms.stomp.Istomp.MessageProducer;
import jms.stomp.Istomp.Session;
import jms.stomp.Istomp.TextMessage;

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

public class StayingProducer extends Thread {

	private Destination destination;
	private long sleepTime;
	private boolean verbose = false;
	private int messageSize = 255;
	private static int parallelThreads = 1;
	private long timeToLive;
	private String user = null;
	private String password = null;
	private String url = null;
	private String subject = "TOOL.DEFAULT";
	private boolean topic;
	private boolean transacted;
	private boolean persistent;
	private static Object lockResults = new Object();
	private String textMessage = null;
	private String clientID = null;
	private boolean connecting;

	private LinkedList<String> pool;
	private LinkedList<String> producting;
	private long sliceSize = 50;

	public StayingProducer() {
		pool = new LinkedList<String>();
		producting = new LinkedList<String>();
	}

	public void run() {
		Connection connection = null;
		Session session = null;
		MessageProducer producer = null;
		try {
			// Create the connection.
			StompConnectionFactory connectionFactory = new StompConnectionFactory(
					user, password, url);
			connection = connectionFactory.createConnection();
			if (this.clientID != null) {

				connection.setClientID(this.clientID);

			} else {

			}
			connection.start();

			// Create the session
			session = connection.createSession(transacted,
					Session.AUTO_ACKNOWLEDGE);
			if (topic) {
				destination = session.createTopic(subject);
			} else {
				destination = session.createQueue(subject);
			}

			// Create the producer.
			producer = session.createProducer(destination);
			if (persistent) {
				producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			} else {
				producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			}
			if (timeToLive != 0) {
				producer.setTimeToLive(timeToLive);
			}

			// Start sending messages
			connecting = true;

			while (true) {

				if (isConnection()) {

					synchronized (this) {
						wait();
					}
				} else {
					break;
				}
				patchSend(session, producer);

			}
			patchSend(session, producer);

		}

		catch (Exception e) {

			e.printStackTrace();
		} finally {
			try {
				producer.close();
				session.close();
				connection.close();

			} catch (Throwable ignore) {
			}
		}
	}

	private void patchSend(Session session, MessageProducer producer)
			throws Exception {
		// System.out.println("awake");
		flowDataStream();
		Iterator<String> itr = producting.iterator();
		while (itr.hasNext()) {

			send(session, producer, itr.next());
		}

	}

	private synchronized void flowDataStream() {

		// System.out.println("length" + pool.size());
		producting.clear();
		Iterator<String> itr = pool.iterator();
		while (itr.hasNext()) {
			producting.add(itr.next());
			itr.remove();
		}

	}

	private boolean isConnection() {

		return connecting;

	}

	public void close() {

		connecting = false;
		synchronized (this) {
			this.notify();
		}

	}

	@Override
	public void start() {

		super.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public synchronized void publicMessage(String textMessage)
			throws IOException {

		if (!isConnection()) {

			throw new IOException();
		}
		sendTextMessage(textMessage);

		// System.out.println(this.isInterrupted());

	}

	protected void send(Session session, MessageProducer producer,
			String context) throws Exception {

		TextMessage message = session.createTextMessage();
		message.setText(context);
		producer.send(message);

	}

	private void sendTextMessage(String textMessage) {

		this.textMessage = textMessage;
		pool.add(textMessage);
		if (pool.size() >= sliceSize) {
			this.notify();

		}

	}

	public void setPersistent(boolean durable) {
		this.persistent = durable;
	}

	public void setMessageSize(int messageSize) {
		this.messageSize = messageSize;
	}

	public void setPassword(String pwd) {
		this.password = pwd;
	}

	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}

	public void setTimeToLive(long timeToLive) {
		this.timeToLive = timeToLive;
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

	public void setClientID(String clientID) {

		this.clientID = clientID;

	}

	/**
	 * 
	 * @param size
	 *            : the message pool containing the max send message; default
	 *            size is 50;
	 */

	public void setPatchSliceSzie(long size) {

		this.sliceSize = size;

	}

}
