package jms.stomp;

import jms.stomp.Istomp.Connection;
import jms.stomp.Istomp.Message;
import jms.stomp.Istomp.MessageConsumer;
import jms.stomp.Istomp.MessageListener;
import jms.stomp.Istomp.Session;
import jms.stomp.Istomp.TextMessage;
import jms.stomp.Istomp.Topic;

import com.android.transport.stomp.Stomp;
import com.android.transport.stomp.StompCommand;

public class test_main {

	public static void main(String[] args) {

		// StompCommand StompCommand = new StompCommand();
		// StompCommand.setUrl("140.134.26.12");
		// StompCommand.setHeader(Stomp.Headers.Connect.CLIENT_ID, "ccc");
		// StompCommand.connect();

		StompConnectionFactory ConnectionFactory = new StompConnectionFactory(
				null, null, "tcp://140.134.26.12:61612");
		Connection mConnection = ConnectionFactory.createConnection();
		mConnection.setClientID("hello");
		mConnection.start();

		Session session = mConnection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("A");

		MessageConsumer messageConsumer = session.createConsumer(topic);
		messageConsumer.setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				TextMessage tm = (TextMessage) message;
				System.out.print("::::" + tm.getText());

			}
		});

	}

}
