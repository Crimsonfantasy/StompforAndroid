package jms.stomp.Istomp;

public interface DeliveryMode {

	static int NON_PERSISTENT = 0;
	// This is the lowest-overhead delivery mode because it does not require
	// that the message be logged to stable storage.
	static int PERSISTENT = 1;

	// This delivery mode instructs the JMS provider to log the message to
	// stable storage as part of the client's send operation.

}
