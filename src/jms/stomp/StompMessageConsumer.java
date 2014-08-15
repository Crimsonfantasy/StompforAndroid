package jms.stomp;

import java.io.IOException;

import com.android.transport.stomp.StompCommand;
import com.android.transport.stomp.StompMessageListener;

import fcu.selab.stomp.Stomp;

import jms.stomp.Istomp.Message;
import jms.stomp.Istomp.MessageConsumer;
import jms.stomp.Istomp.MessageListener;

public class StompMessageConsumer implements MessageConsumer {

	private StompCommand stopCommand;
	private MessageListener messageListener;
	private boolean continuing = false;

	public StompMessageConsumer(StompCommand stopCommand) {

		this.stopCommand = stopCommand;
		System.out.println("StompMessageConsumer");
		new Thread(new SubjectMessageRunnable()).start();

	}

	private class ListenMOM implements StompMessageListener {
		StompTextMessage textMessage;

		public ListenMOM() {

			textMessage = new StompTextMessage();

		}

		@Override
		public void onReadHeader(String newMessage) {

			if (newMessage.startsWith(Stomp.Headers.Message.MESSAGE_ID)) {

				textMessage.setStringProperty(Message.MESSAGE_ID,
						newMessage.substring(Stomp.Headers.Message.MESSAGE_ID
								.length() + 1));

			} else if (newMessage.startsWith(Stomp.Headers.Message.DESTINATION)) {

				textMessage.setStringProperty(Message.DESTINATION,
						newMessage.substring(Stomp.Headers.Message.DESTINATION
								.length() + 1));

			} else if (newMessage.startsWith(Stomp.Headers.Message.TIMESTAMP)) {

				textMessage.setStringProperty(Message.TIMESTAMP,
						newMessage.substring(Stomp.Headers.Message.TIMESTAMP
								.length() + 1));

			} else if (newMessage
					.startsWith(Stomp.Headers.Message.EXPIRATION_TIME)) {

				textMessage
						.setStringProperty(
								Message.EXPIRATION_TIME,
								newMessage
										.substring(Stomp.Headers.Message.EXPIRATION_TIME
												.length() + 1));

			} else if (newMessage.startsWith(Stomp.Headers.Message.PRORITY)) {

				textMessage.setStringProperty(Message.PRORITY, newMessage
						.substring(Stomp.Headers.Message.PRORITY.length() + 1));
			}

		}

		@Override
		public void onReadMessage(String newMessage) {

			textMessage.setText(newMessage);
			if (messageListener != null) {
				messageListener.onMessage((Message) textMessage);
			}

		}
	}

	private class SubjectMessageRunnable implements Runnable {

		@Override
		public void run() {

			stopCommand.subject();
			continuing = true;

			while (continuing) {
				try {
					stopCommand.read(new ListenMOM());

				} catch (IOException e) {
					continuing = false;
					// e.printStackTrace();
					System.out.println("enforce reading  close");
					return;

				}
			}

		}

	}

	@Override
	public void close() {
		continuing = false;
		this.stopCommand.disconnect();

	}

	@Override
	public MessageListener getMessageListener() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMessageSelector() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message receive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message receive(long timeout) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message receiveNoWait() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMessageListener(MessageListener listener) {

		this.messageListener = listener;

	}

}
