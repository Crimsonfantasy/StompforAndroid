package jms.stomp.Istomp;

public interface Connection {

	public void close();

	public Session createSession(boolean transacted, int acknowledgeMode);

	public String getClientID();

	public void setClientID(String clientID);

	public void start();

}
