package jms.stomp;

import jms.stomp.Istomp.Connection;
import jms.stomp.Istomp.Session;

import com.android.transport.stomp.StompCommand;
import fcu.selab.stomp.Stomp;

public class StompConnection implements Connection {

	StompCommand stompCommand;
	private String IP;
	private int port;
	private String clientID;

	/**
	 * 
	 * @param usr
	 *            : user's account , assign null if no account system.
	 * @param passcode
	 *            : usr's password ,assign null if no account system.
	 * @param url
	 *            : url of server
	 */

	public StompConnection(String usr, String passcode, String IP, int port) {

		stompCommand = new StompCommand();
		if (usr != null) {
			stompCommand.setHeader(Stomp.Headers.Connect.LOGIN, usr);
		}
		if (passcode != null) {
			stompCommand.setHeader(Stomp.Headers.Connect.PASSCODE, passcode);
		}
		this.IP = IP;
		this.port = port;

	}

	@Override
	public void close() {

		stompCommand.disconnect();

	}

	@Override
	public Session createSession(boolean transacted, int acknowledgeMode) {

		this.stompCommand.newCommand();

		StompSession newSession = new StompSession(stompCommand, transacted,
				acknowledgeMode);
		return (Session) newSession;

	}

	@Override
	public String getClientID() {

		return this.clientID;

	}

	@Override
	public void setClientID(String clientID) {

		this.clientID = clientID;
		stompCommand.setHeader(Stomp.Headers.Connect.CLIENT_ID, clientID);

	}

	@Override
	public void start() {

		stompCommand.setHost(IP, port);

		stompCommand.connect();
	}

}
