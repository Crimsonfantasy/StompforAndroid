package jms.stomp;

import com.android.transport.stomp.StompCommand;

import jms.stomp.Istomp.Message;
import jms.stomp.Istomp.MessageListener;
import jms.stomp.Istomp.TopicSubscriber;

public class StompTopicSubscriber extends StompMessageConsumer implements
		TopicSubscriber {

	public StompTopicSubscriber(StompCommand stopCommand) {
		super(stopCommand);

	}

}
