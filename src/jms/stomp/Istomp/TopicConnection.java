package jms.stomp.Istomp;

public interface TopicConnection {

	public void close();

	public Session createSession(boolean transacted, int acknowledgeMode);

}
