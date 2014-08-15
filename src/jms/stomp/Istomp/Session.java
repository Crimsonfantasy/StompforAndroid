package jms.stomp.Istomp;

public interface Session {

	static int AUTO_ACKNOWLEDGE = 0;
	// With this acknowledgment mode, the session automatically acknowledges a
	// client's receipt of a message either when the session has successfully
	// returned from a call to receive or when the message listener the session
	// has called to process the message successfully returns.
	static int CLIENT_ACKNOWLEDGE = 1;
	// With this acknowledgment mode, the client acknowledges a consumed message
	// by calling the message's acknowledge method.
	static int DUPS_OK_ACKNOWLEDGE = 2;
	// This acknowledgment mode instructs the session to lazily acknowledge the
	// delivery of messages.
	static int SESSION_TRANSACTED = 3;

	// This value is returned from the method getAcknowledgeMode if the session
	// is transacted.

	// Queue createQueue(String queueName) ;

	Topic createTopic(String topicName);

	Queue createQueue(String queueName);

	// Creates a queue identity given a Queue name.

	MessageProducer createProducer(Destination destination);

	// Creates a MessageProducer to send messages to the specified destination.

	MessageConsumer createConsumer(Destination destination);

	// Creates a MessageConsumer for the specified destination.

	TopicSubscriber createDurableSubscriber(Topic topic, String name);

	// Creates a durable subscriber to the specified topic.

	void close();

	// Closes the session.

	TextMessage createTextMessage();

	// Creates a TextMessage object.

	void unsubscribe(String name);
	// Unsubscribes a durable subscription that has been created by a client.
}
