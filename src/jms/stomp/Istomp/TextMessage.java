package jms.stomp.Istomp;

public interface TextMessage extends Message {

	String getText();

	// Gets the string containing this message's data.
	void setText(String string);
	// Sets the string containing this message's data.

}
