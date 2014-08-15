package com.android.transport.stomp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;

import jms.stomp.Istomp.MessageListener;

import fcu.selab.stomp.Stomp;
import fcu.selab.stomp.StompFrame;

public class StompCommand {

	private HashMap<String, String> header = new HashMap<String, String>();
	private Socket sock;
	private OutputStream outputStream;
	boolean connecting = false;
	private BufferedReader is;
	private String IP;
	private int port = 61612;

	public void connect() {
		if (isConnecting() == true)
			return;

		try {
			sock = new Socket(this.IP, this.port);
			System.out.println("connect");
			is = new BufferedReader(
					new InputStreamReader(sock.getInputStream()));
			sendCommand(Stomp.Commands.CONNECT, header);

		} catch (Exception e) {

			e.printStackTrace();
			connecting = false;
		}

		try {
			read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connecting = true;

	}

	public void send(String context) {

		if (isConnecting() == false)
			return;

		try {

			sendCommand(Stomp.Commands.SEND, header, context);

		} catch (Exception e) {

			e.printStackTrace();
			connecting = false;
		}

		connecting = true;

	}

	public void subject() {
		if (isConnecting() == false)
			return;

		try {

			sendCommand(Stomp.Commands.SUBSCRIBE, header);

		} catch (Exception e) {

			e.printStackTrace();
			connecting = false;
		}

		connecting = true;

	}

	public void unsubscribe() {

		try {
			sendCommand(Stomp.Commands.UNSUBSCRIBE, header);
		} catch (Exception e) {

		}
		try {
			read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void read(StompMessageListener messageListener) throws IOException {

		while (connecting) {

			String buf = is.readLine();
			if (Stomp.NULL.equals(buf)) {
				break;
			}

			if (buf.length() == 0) {

				boolean firstLine = true;
				buf = is.readLine();
				String message = new String();
				while (true) {

					if (buf.endsWith(Stomp.NULL) == true) {

						if (firstLine == true) {
							buf = buf.substring(0, buf.length() - 1);

						} else {

							buf = "\n" + buf.substring(0, buf.length() - 1);

						}
						messageListener.onReadMessage(message + buf);
						break;
					} else {

						if (firstLine == false) {

							buf = "\n" + buf.substring(0, buf.length());

						} else {

							buf = buf.substring(0, buf.length());

						}
						message = message + buf;

					}
					firstLine = false;
					buf = is.readLine();
				}
			} else {
				messageListener.onReadHeader(buf);
			}

		}

	}

	private void read() throws IOException {

		while (true) {
			String buf = is.readLine();
			System.out.println(":" + buf);
			if (Stomp.NULL.equals(buf)) {

				break;
			}
		}

	}

	public void setHost(String IP, int port) {

		this.IP = IP;
		this.port = port;

	}

	private void sendCommand(String command, HashMap<String, String> header,
			String context) throws Exception {

		StompFrame frame = new StompFrame(command, header);
		frame.setContent(context.getBytes("UTF-8"));
		// System.out.print(frame.format());
		sendFrame(sock, frame.format());

	}

	private void sendCommand(String command, HashMap<String, String> header)
			throws Exception {

		StompFrame frame = new StompFrame(command, header);
		System.out.print(frame.format());
		sendFrame(sock, frame.format());

	}

	/**
	 * 
	 * @return true for connecting , else flase;
	 */

	private boolean isConnecting() {

		return connecting;
	}

	public void newCommand() {

		header.clear();

	}

	public void disconnect() {

		if (isConnecting()) {

			StompFrame frame = new StompFrame(Stomp.Commands.DISCONNECT);
			System.out.println(frame.format());
			try {
				sendFrame(sock, frame.format());
			} catch (Exception e) {

				e.printStackTrace();
			} finally {
				try {

					outputStream.close();
					sock.close();

				} catch (IOException e) {

					e.printStackTrace();
				}

			}
		}
		connecting = false;

	}

	private void sendFrame(Socket sock, String frame) throws Exception {

		byte[] bytes = frame.getBytes("UTF-8");
		outputStream = sock.getOutputStream();
		outputStream.write(bytes);
		outputStream.write(Stomp.NULL.getBytes("UTF-8"));
		outputStream.flush();
	}

	public void setHeader(String headerName, String value) {

		header.put(headerName, value);

	}

	public void removeHeader(String headerName) {

		header.remove(headerName);

	}

}
