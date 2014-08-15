import com.thetransactioncompany.jsonrpc2.*;

import java.util.*;

/** 
 * This example illustrates a typical life-cycle of a JSON-RPC 2.0 request:
 * <ul>
 *     <li>Client side: Create new request
 *     <li>Client side: Serialise request to string and send
 *     <li>Server side: Parse received string back to request object
 *     <li>Server side: Get the request data
 *     <li>Server side: Create a response
 *     <li>Server side: Serialise response to string and send back
 *     <li>Client side: Parse received string back to response object
 *     <li>Client side: Check the response for success, get the
 *         result/error
 * </ul>
 *
 * @author Vladimir Dzhuvinov
 * @version 2009-12-03
 */
public class Example1 {

	public static void main(String[] args) {
	
		// The remote method to call
		String method = "makePayment";
		
		// The required parameters to pass
		Map params = new HashMap();
		params.put("recipient", "Penny Adams");
		params.put("amount", 175.05);
		
		// The mandatory request ID
		String id = "req-001";
		
		System.out.println("Creating new request with properties :");
		System.out.println("\tmethod     : " + method);
		System.out.println("\tparameters : " + params);
		System.out.println("\tid         : " + id + "\n\n");
		
		// Create request
		JSONRPC2Request reqOut = new JSONRPC2Request(method, params, id);
		
		
		// Serialise request to JSON-encoded string
		String jsonString = reqOut.toString();
		
		System.out.println("Serialised request to JSON-encoded string :");
		System.out.println("\t" + jsonString + "\n\n");
		
		
		// The string can now be dispatched to the server...
		
	
		// Parse request string
		JSONRPC2Request reqIn = null;
		
		try {
			reqIn = JSONRPC2Request.parse(jsonString);
		} catch (JSONRPC2ParseException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		// Extract the request data
		System.out.println("Parsed request with properties :");
		System.out.println("\tmethod     : " + reqIn.getMethod());
		System.out.println("\tparameters : " + reqIn.getParams());
		System.out.println("\tid         : " + reqIn.getID() + "\n\n");
		
		
		// Process request...
		
		
		// The request result
		String result = "payment-id-001";
		
		System.out.println("Creating response with properties : ");
		System.out.println("\tresult : " + result);
		System.out.println("\tid     : " + reqIn.getID() + "\n\n"); // ID must be echoed back
		
		
		// Create response
		JSONRPC2Response respOut = new JSONRPC2Response(result, reqIn.getID());
		
		
		// Serialise response to JSON-encoded string
		jsonString = respOut.toString();
		
		System.out.println("Serialised response to JSON-encoded string :");
		System.out.println("\t" + jsonString + "\n\n");
		
		
		// The response can now be sent back to the client...
		
		
		// Parse response string
		JSONRPC2Response respIn = null;
		
		try {
			respIn = JSONRPC2Response.parse(jsonString);
		} catch (JSONRPC2ParseException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		
		// Check for success or error
		if (respIn.indicatesSuccess()) {
			System.out.println("The request succeeded :");
			
			System.out.println("\tresult : " + respIn.getResult());
			System.out.println("\tid     : " + respIn.getID());
		}
		else {
			System.out.println("The request failed :");
			
			JSONRPC2Error err = respIn.getError();
			
			System.out.println("\terror.code    : " + err.getCode());
			System.out.println("\terror.message : " + err.getMessage());
			System.out.println("\terror.data    : " + err.getData());
		}
	}
}
