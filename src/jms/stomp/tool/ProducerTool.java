package jms.stomp.tool;

import jms.stomp.StompConnectionFactory;
import jms.stomp.Istomp.Connection;
import jms.stomp.Istomp.DeliveryMode;
import jms.stomp.Istomp.Destination;
import jms.stomp.Istomp.MessageProducer;
import jms.stomp.Istomp.Session;
import jms.stomp.Istomp.TextMessage;

/**
 * A simple tool for publishing messages
 * 
 * 
 */
public class ProducerTool extends Thread {

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

	public ProducerTool() {

	}

	public void run() {
		Connection connection = null;
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
			Session session = connection.createSession(transacted,
					Session.AUTO_ACKNOWLEDGE);
			if (topic) {
				destination = session.createTopic(subject);
			} else {
				destination = session.createQueue(subject);
			}

			// Create the producer.
			MessageProducer producer = session.createProducer(destination);
			if (persistent) {
				producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			} else {
				producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			}
			if (timeToLive != 0) {
				producer.setTimeToLive(timeToLive);
			}

			// Start sending messages
			send(session, producer);

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (Throwable ignore) {
			}
		}
	}

	protected void send(Session session, MessageProducer producer)
			throws Exception {

		TextMessage message = session.createTextMessage();
		message.setText(this.textMessage);
		producer.send(message);

		// if (transacted) {
		// log("[" + this.getName() + "] Committing " +
		// messageCount + " messages");
		// session.commit();
		// }
		// Thread.sleep(sleepTime);

	}

	public void setTextMessage(String textMessage) {

		this.textMessage = textMessage;

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

}
