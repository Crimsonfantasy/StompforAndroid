package jms.stomp;

import jms.stomp.Istomp.Queue;

public class StompQueue implements Queue {

	String queueName;
	String queueTag = " /queue/";

	public StompQueue(String queueName) {

		this.queueName = queueName;
	}

	@Override
	public String getTopicName() {

		return queueName;
	}

	@Override
	public String toString() {

		return queueTag + getTopicName();

	}
	// Returns a string representation of this object.

}
