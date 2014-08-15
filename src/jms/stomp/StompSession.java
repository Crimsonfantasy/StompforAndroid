package jms.stomp;

import com.android.transport.stomp.StompCommand;

import fcu.selab.stomp.Stomp;
import jms.stomp.Istomp.Destination;
import jms.stomp.Istomp.MessageConsumer;
import jms.stomp.Istomp.MessageProducer;
import jms.stomp.Istomp.Queue;
import jms.stomp.Istomp.Session;
import jms.stomp.Istomp.TextMessage;
import jms.stomp.Istomp.Topic;
import jms.stomp.Istomp.TopicSubscriber;

public class StompSession implements Session {

	private int ackMode = -1;
	private boolean isTransaction = false;
	private StompCommand stompCommand;

	public StompSession(StompCommand stompCommand, boolean isTransaction,
			int ackMode) {

		this.ackMode = ackMode;
		this.isTransaction = isTransaction;
		this.stompCommand = stompCommand;

	}

	@Override
	public Topic createTopic(String topicName) {

		return (Topic) new StompTopic(topicName);

	}

	@Override
	public Queue createQueue(String queueName) {

		return (Queue) new StompQueue(queueName);

	}

	private void addAckHeader() {
		try {

			switch (ackMode) {

			case Session.AUTO_ACKNOWLEDGE:
				stompCommand.setHeader(Stomp.Headers.Subscribe.ACK_MODE,
						Stomp.Headers.Subscribe.AckModeValues.AUTO);
				break;
			case Session.CLIENT_ACKNOWLEDGE:
				stompCommand.setHeader(Stomp.Headers.Subscribe.ACK_MODE,
						Stomp.Headers.Subscribe.AckModeValues.CLIENT);
				break;
			case Session.DUPS_OK_ACKNOWLEDGE:
				throw new Exception(
						"Stomp do not support DUPS_OK_ACKNOWLEDGE values");

			case Session.SESSION_TRANSACTED:

				throw new Exception(
						"Stomp do not support SESSION_TRANSACTED values");

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public MessageProducer createProducer(Destination destination) {

		this.stompCommand.setHeader(Stomp.Headers.Send.DESTINATION,
				destination.toString());
		return (MessageProducer) new StompMessageProducer(stompCommand);
	}

	@Override
	public MessageConsumer createConsumer(Destination destination) {

		addAckHeader();
		this.stompCommand.setHeader(Stomp.Headers.Subscribe.DESTINATION,
				destination.toString());
		return (MessageConsumer) new StompMessageConsumer(stompCommand);
	}

	@Override
	public TopicSubscriber createDurableSubscriber(Topic topic, String name) {

		addAckHeader();
		this.stompCommand.setHeader(Stomp.Headers.Subscribe.DESTINATION,
				topic.toString());
		this.stompCommand.setHeader("activemq.subscriptionName", name);
		return (TopicSubscriber) new StompTopicSubscriber(stompCommand);
	}

	@Override
	public void close() {

		stompCommand.disconnect();

	}

	@Override
	public TextMessage createTextMessage() {

		return new StompTextMessage();
	}

	@Override
	public void unsubscribe(String name) {

		stompCommand.setHeader("activemq.subscriptionName", name);
		stompCommand.setHeader(Stomp.Headers.Subscribe.ID, name);

		stompCommand.unsubscribe();

	}
}
