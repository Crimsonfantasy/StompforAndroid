package fcu.selab.stomp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * A simple implementation of a STMOP client using pure basic Java API Please
 * refere to http://stomp.github.com//stomp-specification-1.0.html
 * 
 * @author Chun-Feng Liao (2012/4/16)
 */
public class StompTestClient {

	public static void main(String[] args) throws UnknownHostException,
			IOException {
		Socket sock = new Socket("140.134.26.12", 61612); // echo server.
		BufferedReader is = new BufferedReader(new InputStreamReader(
				sock.getInputStream()));
		PrintWriter os = new PrintWriter(sock.getOutputStream(), true);

		// Get Session ID

		// CONNECT
		//
		// null
		os.println(Stomp.Commands.CONNECT);
		os.print("\n");
		os.println(Stomp.NULL);
		os.flush();

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
		System.out.println("==>Got Session Id=[" + sessionId + "]");

		// Send A Message

		// SEND
		// destination:/topic/ssh.CONTEXT
		// \n
		// Hello Topic
		// null
		os.println(Stomp.Commands.SEND);
		os.println(Stomp.Headers.Send.DESTINATION + ": /topic/A");
		os.print("\n");
		os.print("Hello Topic");// Text to send
		os.println(Stomp.NULL);
		os.flush();

		// Subscribe to a topic

		// SUBSCRIBE
		// destination:/topic/ssh.CONTEXT
		// \n
		// ack: auto
		// null
		os.println(Stomp.Commands.SUBSCRIBE);
		os.println(Stomp.Headers.Subscribe.DESTINATION + ": /topic/A");
		os.println(Stomp.Headers.Subscribe.ACK_MODE + ": "
				+ Stomp.Headers.Subscribe.AckModeValues.AUTO);
		os.print("\n");
		os.println(Stomp.NULL);
		os.flush();

		// Get Message
		while (true) {
			System.out.print("ccc");
			String line = is.readLine();
			System.out.println("cc" + line);
			if (Stomp.NULL.equals(line))
				break;
		}
	}
}
