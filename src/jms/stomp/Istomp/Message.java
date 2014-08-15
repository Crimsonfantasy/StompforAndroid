package jms.stomp.Istomp;

public interface Message {

	static String MESSAGE_ID = "message-id";
	static String DESTINATION = "destination";
	static String CORRELATION_ID = "correlation-id";
	static String EXPIRATION_TIME = "expires";
	static String REPLY_TO = "reply-to";
	static String PRORITY = "priority";
	static String REDELIVERED = "redelivered";
	static String TIMESTAMP = "timestamp";
	static String TYPE = "type";
	static String SUBSCRIPTION = "subscription";
	static String USERID = "JMSXUserID";
	static String ORIGINAL_DESTINATION = "original-destination";

	String getStringProperty(String name);

	// Returns the value of the String property with the specified name.
	boolean propertyExists(String name);

	// Indicates whether a property value exists.

	void setJMSPriority(int priority);

	// Sets the priority level for this message.
	void setStringProperty(String name, String value);

	// Sets a String property value with the specified name into the message.

	public long getJMSTimestamp();
	// Gets the message timestamp.

}
