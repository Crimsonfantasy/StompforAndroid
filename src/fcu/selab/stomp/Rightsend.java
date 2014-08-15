package fcu.selab.stomp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

/**
 * A simple implementation of a STMOP client using pure basic Java API Please
 * refere to http://stomp.github.com//stomp-specification-1.0.html
 * 
 * @author Chun-Feng Liao (2012/4/16)
 */
public class Rightsend {

	public static void main(String[] args) throws Exception {
		Socket sock = new Socket("140.134.26.12", 61612); // echo server.
		BufferedReader is = new BufferedReader(new InputStreamReader(
				sock.getInputStream()));
		// PrintWriter os = new PrintWriter(sock.getOutputStream(), true);

		// Get Session ID

		// CONNECT
		//
		// null

		// os.println(Stomp.Commands.CONNECT);
		// os.print("\n");
		// os.println(Stomp.NULL);
		// os.flush();
		//

		HashMap<String, String> connectheaders = new HashMap<String, String>();

		// connectheaders.put(Stomp.Headers.Connect.CLIENT_ID, " DAC");

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
		System.out.println("==>Got Session Id=[" + sessionId + "]");

		// Send A Message

		// SEND
		// destination:/topic/ssh.CONTEXT
		// \n
		// Hello Topic
		// null
		// os.println(Stomp.Commands.SEND);
		// os.println(Stomp.Headers.Send.DESTINATION + ": /topic/A");
		// os.print("\n");
		// os.print("Hello Topic^^");// Text to send
		// os.println(Stomp.NULL);
		// os.flush();
		// os.close();
		// sock.close();

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put(Stomp.Headers.Send.DESTINATION, "/topic/A");
		StompFrame frame = new StompFrame(Stomp.Commands.SEND, headers);
		System.out.print(frame.format());
		String data = new String("^?^??\n\n");
		frame.setContent(data.getBytes("UTF-8"));
		sendFrame(sock, frame.format());

		disConnect(sock);

		// read(sock);

		// os.close();
		sock.close();
	}

	public static void sendFrame(Socket sock, String frame) throws Exception {
		byte[] bytes = frame.getBytes("UTF-8");
		OutputStream outputStream = sock.getOutputStream();
		outputStream.write(bytes);
		outputStream.write(Stomp.NULL.getBytes("UTF-8"));
		outputStream.flush();
	}

	public static void disConnect(Socket sock) throws Exception {

		StompFrame frame = new StompFrame(Stomp.Commands.DISCONNECT);
		System.out.println(frame.format());
		sendFrame(sock, frame.format());

	}

	public static void read(Socket sock) throws IOException {
		BufferedReader is = new BufferedReader(new InputStreamReader(
				sock.getInputStream()));
		while (true) {
			String line = is.readLine();
			System.out.println(":" + line);
			if (Stomp.NULL.equals(line))
				break;
		}

	}

}
