package jms.stomp;

import jms.stomp.Istomp.Topic;

public class StompTopic implements Topic {

	String topicName;
	String topicTag = " /topic/";

	public StompTopic(String topicName) {

		this.topicName = topicName;
	}

	@Override
	public String getTopicName() {

		return topicName;
	}

	@Override
	public String toString() {

		return topicTag + getTopicName();

	}
	// Returns a string representation of this object.

}
