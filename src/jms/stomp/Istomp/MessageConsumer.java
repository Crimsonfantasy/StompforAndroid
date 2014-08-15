package jms.stomp.Istomp;

public interface MessageConsumer {
	void close();

	// Closes the message consumer.
	MessageListener getMessageListener();

	// Gets the message consumer's MessageListener.
	String getMessageSelector();

	// Gets this message consumer's message selector expression.
	Message receive();

	// Receives the next message produced for this message consumer.
	Message receive(long timeout);

	// Receives the next message that arrives within the specified timeout
	// interval.
	Message receiveNoWait();

	// Receives the next message if one is immediately available.
	void setMessageListener(MessageListener listener);
	// Sets the message consumer's MessageListener.

}
