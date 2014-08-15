package jms.stomp;

import jms.stomp.Istomp.Connection;
import jms.stomp.Istomp.ConnectionFactory;

public class StompConnectionFactory implements ConnectionFactory {

	private String usr = null;
	private String passcode = null;
	private String url = null;

	/**
	 * 
	 * @param usr
	 *            : user's account , assign null if no account system.
	 * @param passcode
	 *            : usr's password ,assign null if no account system.
	 * @param url
	 *            : url of server
	 */
	public StompConnectionFactory(String usr, String passcode, String url) {

		this.usr = usr;
		this.passcode = passcode;
		if (url == null) {
			throw new IllegalArgumentException("url must be assigned !!");
		}
		this.url = url;
	}

	public Connection createConnection() {

		String[] splitestr = url.split(":");
		String IP = splitestr[1].substring(2);
		int port = Integer.parseInt(splitestr[2]);
		Connection newConnection = new StompConnection(usr, passcode, IP, port);
		return (Connection) newConnection;

	}

}
