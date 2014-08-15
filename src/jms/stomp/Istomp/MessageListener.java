package jms.stomp.Istomp;

public interface MessageListener {

	void onMessage(Message message);
	// Passes a message to the listener.

}
