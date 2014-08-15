package jms.stomp.Istomp;

public interface MessageProducer {

	void close();

	// Closes the message producer.
	int getDeliveryMode();

	// Gets the producer's default delivery mode.
	Destination getDestination();

	// Gets the destination associated with this MessageProducer.
	boolean getDisableMessageID();

	// Gets an indication of whether message IDs are disabled.
	boolean getDisableMessageTimestamp();

	// Gets an indication of whether message timestamps are disabled.
	int getPriority();

	// Gets the producer's default priority.
	long getTimeToLive();

	// Gets the default length of time in milliseconds from its dispatch time
	// that a produced message should be retained by the message system.

	void setDeliveryMode(int deliveryMode);

	// Sets the producer's default delivery mode.
	void setDisableMessageID(boolean value);

	// Sets whether message IDs are disabled.
	void setDisableMessageTimestamp(boolean value);

	// Sets whether message timestamps are disabled.
	void setPriority(int defaultPriority);

	// Sets the producer's default priority.
	void setTimeToLive(long timeToLive);

	// Sets the default length of time in milliseconds from its dispatch time
	// that a produced message should be retained by the message system.

	void send(Message message);
	// Sends a message using the MessageProducer's default delivery mode,
	// priority, and time to live.

}
