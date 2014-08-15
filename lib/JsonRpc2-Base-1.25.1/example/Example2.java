import com.thetransactioncompany.jsonrpc2.*;

import java.util.*;

/** 
 * This example shows how to discern between received JSON-RPC 2.0 requests 
 * and notifications on the server side
 *
 * @author Vladimir Dzhuvinov
 * @version 2009-12-03
 */
public class Example2 {

	/** Parses a JSON-RPC 2.0 message and determines its type
	 *
	 * @param jsonString The JSON-RPC 2.0 message encoded as
	 *        JSON string
	 */
	public static void parseMessage(String jsonString) {
	
		JSONRPC2Message msg = null;
		
		try {
			msg = JSONRPC2Message.parse(jsonString);
			
		} catch (JSONRPC2ParseException e) {
			System.out.println(e);
			return;
		}
		
		if (msg instanceof JSONRPC2Request) {
			System.out.println("The message is a Request");
		}
		else if (msg instanceof JSONRPC2Notification) {
			System.out.println("The message is a Notification");
		}
		else if (msg instanceof JSONRPC2Response) {
			System.out.println("The message is a Response");
		}
	}


	public static void main(String[] args) {
	
		// Create request
		String method = "getAccountBalance";
		Map params = new HashMap();
		params.put("accountHolder", "Penny Adams");
		String id = "req-002";
		JSONRPC2Request request = new JSONRPC2Request(method, params, id);
		
		// Create notification
		method = "notifyRecipient";
		params = new HashMap();
		params.put("recipient", "John Shilling");
		JSONRPC2Notification notification = new JSONRPC2Notification(method, params);
		
		// Create response
		String result = "EUR 1500.35";
		JSONRPC2Response response = new JSONRPC2Response(result, id);

		parseMessage(request.toString());
		parseMessage(notification.toString());
		parseMessage(response.toString());
	}
}
