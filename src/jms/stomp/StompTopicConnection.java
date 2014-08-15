package jms.stomp;

import jms.stomp.Istomp.Session;
import jms.stomp.Istomp.TopicConnection;

import com.android.transport.stomp.StompCommand;

public class StompTopicConnection implements TopicConnection {

	StompCommand stompCommand;

	public StompTopicConnection() {

		stompCommand = new StompCommand();

	}

	/*
	 * close connection
	 * 
	 * @see javax.jms.Connection#close()
	 */
	@Override
	public void close() {

		stompCommand.disconnect();

	}

	/*
	 * yet (non-Javadoc)
	 * 
	 * @see javax.jms.Connection#createConnectionConsumer(javax.jms.Destination,
	 * java.lang.String, javax.jms.ServerSessionPool, int)
	 */

	@Override
	public Session createSession(boolean transacted, int acknowledgeMode) {

		stompCommand.newCommand();
		if (transacted) {

		}
		return null;
	}

}
