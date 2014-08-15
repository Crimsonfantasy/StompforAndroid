package fcu.selab.stomp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

public class Subscribe {

	public static void main(String[] args) throws Exception {

		Socket sock = new Socket("140.134.26.12", 61612); // echo server.
		BufferedReader is = new BufferedReader(new InputStreamReader(
				sock.getInputStream()));
		PrintWriter os = new PrintWriter(sock.getOutputStream(), true);

		// Get Session ID

		// CONNECT
		//
		// null

		HashMap<String, String> connectheaders = new HashMap<String, String>();

		connectheaders.put(Stomp.Headers.Connect.CLIENT_ID, "cheng");

		StompFrame connectframe = new StompFrame(Stomp.Commands.CONNECT,
				connectheaders);

		System.out.print(connectframe.format());
		sendFrame(sock, connectframe.format());

		// os.println(Stomp.Commands.CONNECT);
		// os.print("\n");
		// os.println(Stomp.NULL);
		// os.flush();

		String sessionId = null;
		while (true) {
			String line = is.readLine();
			System.out.println(line);
			if (line.startsWith("session")) {
				sessionId = line.substring(8);
			}
			if (Stomp.NULL.equals(line))
				break;
		}

		/**
		 * 
		 * subscribe
		 */

		// os.println(Stomp.Commands.SUBSCRIBE);
		// os.println(Stomp.Headers.Subscribe.DESTINATION + ": /topic/A");
		// os.println(Stomp.Headers.Subscribe.ACK_MODE + ": "
		// + Stomp.Headers.Subscribe.AckModeValues.AUTO);
		// os.print("\n");
		// os.println(Stomp.NULL);
		// os.flush();

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put(Stomp.Headers.Subscribe.DESTINATION, "/topic/A");
		headers.put(Stomp.Headers.Subscribe.ACK_MODE,
				Stomp.Headers.Subscribe.AckModeValues.AUTO);
		headers.put(Stomp.Headers.Subscribe.ID, "yu-sheng");
		StompFrame frame = new StompFrame(Stomp.Commands.SUBSCRIBE, headers);

		/**
		 * 
		 * take a new function durable subscription for this For durable topic
		 * subscriptions you must specify the same clientId on the connection
		 * and subcriptionName on the subscribe.
		 * 
		 */
		headers.put("activemq.subscriptionName", "cheng");
		System.out.print(frame.format());
		sendFrame(sock, frame.format());

		// Get Message
		while (true) {
			// String line = is.readLine();
			// System.out.println(":" + line);
			// if (Stomp.NULL.equals(line))
			// break;
			int a = is.read();
			System.out.print("--" + Integer.toString(a) + "--");
		}

	}

	public static void sendFrame(Socket sock, String frame) throws Exception {
		byte[] bytes = frame.getBytes("UTF-8");
		OutputStream outputStream = sock.getOutputStream();
		outputStream.write(bytes);
		outputStream.write(Stomp.NULL.getBytes("UTF-8"));
		outputStream.flush();
	}

}
