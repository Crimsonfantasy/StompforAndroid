package fcu.selab.stomp;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class Unsubscribe {

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

		// connectheaders.put(Stomp.Headers.Connect.CLIENT_ID, " cheng");
		StompFrame connectframe = new StompFrame(Stomp.Commands.CONNECT,
				connectheaders);
		System.out.print(connectframe.format());

		sendFrame(sock, connectframe.format());

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
		 * unSubscribe
		 */

		// os.println(Stomp.Commands.UNSUBSCRIBE);
		// os.println("ID:" +
		// "Yu-ShengCheng.linux.org.tw-49677-1334674082291-2:29");
		// os.println(Stomp.Headers.Subscribe.DESTINATION + ": /topic/");
		// os.print("\n");
		// os.println(Stomp.NULL);
		// os.flush();

		HashMap<String, String> headers = new HashMap<String, String>();

		// headers.put(Stomp.Headers.Unsubscribe.ID, "cheng");
		headers.put(Stomp.Headers.Unsubscribe.DESTINATION, "/topic/A");

		StompFrame frame = new StompFrame(Stomp.Commands.UNSUBSCRIBE, headers);
		/**
		 * 
		 * remove durable subscription
		 * 
		 * headers.put("activemq.subscriptionName", "cheng");
		 */
		System.out.print(frame.format());

		sendFrame(sock, frame.format());

		while (true) {
			String line = is.readLine();
			System.out.println(":" + line);
			if (Stomp.NULL.equals(line))
				break;
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
