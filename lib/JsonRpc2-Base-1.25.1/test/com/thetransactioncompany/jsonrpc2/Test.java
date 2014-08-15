package com.thetransactioncompany.jsonrpc2;


import java.util.*;

import junit.framework.*;


/**
 * JUnit tests for the base JSON-RPC 2.0 classes.
 *
 * @author Vladimir Dzhuvinov
 * @version 1.25.1.1 (2011-08-05)
 */
public class Test extends TestCase {

	
	public Test(String name) {
	
		super(name);
	}


	public void testNoParamsRequest() {
	
		// Create new request with no params
		final String method = "rpc.test";
		final long id = 1;
		
		JSONRPC2Request request = new JSONRPC2Request(method, id);
		
		// Verify request members
		assertEquals(JSONRPC2ParamsType.NO_PARAMS, request.getParamsType());
		assertEquals(method, request.getMethod());
		assertEquals(id, request.getID());
		
		// Serialise to JSON string
		String json = request.toString();
		
		// Parse back
		try {
			request = JSONRPC2Request.parse(json);
			
		} catch (JSONRPC2ParseException e) {
			fail(e.getMessage());
		}
		
		// Verify parsed request members
		assertEquals(JSONRPC2ParamsType.NO_PARAMS, request.getParamsType());
		assertEquals(method, request.getMethod());
		assertEquals(id, request.getID());
	}
	
	
	public void testNoParamsRequest2() {
	
		// Create new request with no params
		final String method = "rpc.test";
		final String id = "0001";
		
		JSONRPC2Request request = new JSONRPC2Request(method, id);
		
		// Verify request members
		assertEquals(JSONRPC2ParamsType.NO_PARAMS, request.getParamsType());
		assertEquals(method, request.getMethod());
		assertEquals(id, request.getID());
		
		// Serialise to JSON string
		String json = request.toString();
		
		// Parse back
		try {
			JSONRPC2Parser parser = new JSONRPC2Parser();
			request = parser.parseJSONRPC2Request(json);
			
		} catch (JSONRPC2ParseException e) {
			fail(e.getMessage());
		}
		
		// Verify parsed request members
		assertEquals(JSONRPC2ParamsType.NO_PARAMS, request.getParamsType());
		assertEquals(method, request.getMethod());
		assertEquals(id, request.getID());
	}
	
	
	public void testArrayParamsRequest() {
	
		// Create new request with array params
		final String method = "rpc.test";
		final long id = 1;
		
		final String param0 = "John Smith";
		final long param1 = 25;
		final boolean param2 = true;
		final double param3 = 3.1415;
		
		List params = new LinkedList();
		params.add(param0);
		params.add(param1);
		params.add(param2);
		params.add(param3);
		
		JSONRPC2Request request = new JSONRPC2Request(method, params, id);
		
		// Verify request members
		assertEquals(JSONRPC2ParamsType.ARRAY, request.getParamsType());
		assertEquals(method, request.getMethod());
		assertEquals(id, request.getID());
		
		List paramsOut = (List)request.getParams();
		assertEquals(param0, paramsOut.get(0));
		assertEquals(param1, paramsOut.get(1));
		assertEquals(param2, paramsOut.get(2));
		assertEquals(param3, paramsOut.get(3));
				
		// Serialise to JSON string
		String json = request.toString();
		
		// Parse back
		try {
			request = JSONRPC2Request.parse(json);
		
		} catch (JSONRPC2ParseException e) {		
			fail(e.getMessage());
		}
		
		// Verify parsed request members
		assertEquals(JSONRPC2ParamsType.ARRAY, request.getParamsType());
		assertEquals(method, request.getMethod());
		assertEquals(id, request.getID());
		
		paramsOut = (List)request.getParams();
		assertEquals(param0, paramsOut.get(0));
		assertEquals(param1, paramsOut.get(1));
		assertEquals(param2, paramsOut.get(2));
		assertEquals(param3, paramsOut.get(3));
	}
	
	
	public void testArrayParamsRequest2() {
	
		// Create new request with array params
		final String method = "rpc.test";
		final long id = 1;
		
		final String param0 = "John Smith";
		final long param1 = 25;
		final boolean param2 = true;
		final double param3 = 3.1415;
		
		List params = new LinkedList();
		params.add(param0);
		params.add(param1);
		params.add(param2);
		params.add(param3);
		
		JSONRPC2Request request = new JSONRPC2Request(method, params, id);
		
		// Verify request members
		assertEquals(JSONRPC2ParamsType.ARRAY, request.getParamsType());
		assertEquals(method, request.getMethod());
		assertEquals(id, request.getID());
		
		List paramsOut = (List)request.getParams();
		assertEquals(param0, paramsOut.get(0));
		assertEquals(param1, paramsOut.get(1));
		assertEquals(param2, paramsOut.get(2));
		assertEquals(param3, paramsOut.get(3));
				
		// Serialise to JSON string
		String json = request.toString();
		
		// Parse back
		try {
			JSONRPC2Parser parser = new JSONRPC2Parser();
			request = parser.parseJSONRPC2Request(json);
		
		} catch (JSONRPC2ParseException e) {		
			fail(e.getMessage());
		}
		
		// Verify parsed request members
		assertEquals(JSONRPC2ParamsType.ARRAY, request.getParamsType());
		assertEquals(method, request.getMethod());
		assertEquals(id, request.getID());
		
		paramsOut = (List)request.getParams();
		assertEquals(param0, paramsOut.get(0));
		assertEquals(param1, paramsOut.get(1));
		assertEquals(param2, paramsOut.get(2));
		assertEquals(param3, paramsOut.get(3));
	}
	
	
	public void testObjectParamsRequest() {
	
		// Create new request with object parameters
		final String method = "rpc.test";
		final long id = 1;
		
		final String paramA = "John Smith";
		final long paramB = 25;
		final boolean paramC = true;
		final double paramD = 3.1415;
		
		Map params = new HashMap();
		params.put("name", paramA);
		params.put("age", paramB);
		params.put("bool", paramC);
		params.put("pi", paramD);
		
		JSONRPC2Request request = new JSONRPC2Request(method, params, id);
		
		// Verify request members
		assertEquals(JSONRPC2ParamsType.OBJECT, request.getParamsType());
		assertEquals(method, request.getMethod());
		assertEquals(id, request.getID());
		
		Map paramsOut = (Map)request.getParams();
		assertEquals(paramA, paramsOut.get("name"));
		assertEquals(paramB, paramsOut.get("age"));
		assertEquals(paramC, paramsOut.get("bool"));
		assertEquals(paramD, paramsOut.get("pi"));
		
		// Serialise to JSON string
		String json = request.toString();
		
		// Parse back
		try {
			request = JSONRPC2Request.parse(json);
		
		} catch (JSONRPC2ParseException e) {		
			fail(e.getMessage());
		}
		
		// Verify parsed request members
		assertEquals(JSONRPC2ParamsType.OBJECT, request.getParamsType());
		assertEquals(method, request.getMethod());
		assertEquals(id, request.getID());
		
		paramsOut = (Map)request.getParams();
		assertEquals(paramA, paramsOut.get("name"));
		assertEquals(paramB, paramsOut.get("age"));
		assertEquals(paramC, paramsOut.get("bool"));
		assertEquals(paramD, paramsOut.get("pi"));
	}
	
	
	public void testObjectParamsRequest2() {
	
		// Create new request with object parameters
		final String method = "rpc.test";
		final long id = 1;
		
		final String paramA = "John Smith";
		final long paramB = 25;
		final boolean paramC = true;
		final double paramD = 3.1415;
		
		Map params = new HashMap();
		params.put("name", paramA);
		params.put("age", paramB);
		params.put("bool", paramC);
		params.put("pi", paramD);
		
		JSONRPC2Request request = new JSONRPC2Request(method, params, id);
		
		// Verify request members
		assertEquals(JSONRPC2ParamsType.OBJECT, request.getParamsType());
		assertEquals(method, request.getMethod());
		assertEquals(id, request.getID());
		
		Map paramsOut = (Map)request.getParams();
		assertEquals(paramA, paramsOut.get("name"));
		assertEquals(paramB, paramsOut.get("age"));
		assertEquals(paramC, paramsOut.get("bool"));
		assertEquals(paramD, paramsOut.get("pi"));
		
		// Serialise to JSON string
		String json = request.toString();
		
		// Parse back
		try {
			JSONRPC2Parser parser = new JSONRPC2Parser();
			request = parser.parseJSONRPC2Request(json);
		
		} catch (JSONRPC2ParseException e) {		
			fail(e.getMessage());
		}
		
		// Verify parsed request members
		assertEquals(JSONRPC2ParamsType.OBJECT, request.getParamsType());
		assertEquals(method, request.getMethod());
		assertEquals(id, request.getID());
		
		paramsOut = (Map)request.getParams();
		assertEquals(paramA, paramsOut.get("name"));
		assertEquals(paramB, paramsOut.get("age"));
		assertEquals(paramC, paramsOut.get("bool"));
		assertEquals(paramD, paramsOut.get("pi"));
	}
	
	
	public void testSuccessResponse() {
		
		// Create new request
		final String result = "This is the request result";
		final long id = 1;
		
		JSONRPC2Response response = new JSONRPC2Response(result, id);
		
		
		// Verify response members
		assertTrue(response.indicatesSuccess());
		assertEquals(result, (String)response.getResult());
		assertEquals(id, response.getID());
		
		
		// Serialise to JSON string
		String json = response.toString();
		
		// Parse back
		try {
			response = JSONRPC2Response.parse(json);
		
		} catch (JSONRPC2ParseException e) {
			assertTrue(false);
		}
		
		// Verify parsed response members
		assertEquals(result, (String)response.getResult());
		assertEquals(id, response.getID());
	}
	
	
	public void testSuccessResponseNullResult() {
	
		Object result = null;
		
		JSONRPC2Response response = new JSONRPC2Response(result, 0);
		
		assertTrue(response.indicatesSuccess());
	}
	
	
	public void testErrorResponse() {
	
		// Create new request indicating error
		JSONRPC2Error err = JSONRPC2Error.METHOD_NOT_FOUND;
		final long id = 1;
		
		JSONRPC2Response response = new JSONRPC2Response(err, id);
		
		// Verify response members
		assertFalse(response.indicatesSuccess());
		err = response.getError();
		assertEquals(JSONRPC2Error.METHOD_NOT_FOUND.getCode(), err.getCode());
		assertEquals(JSONRPC2Error.METHOD_NOT_FOUND.getMessage(), err.getMessage());
		assertEquals(id, response.getID());
		
		
		// Serialise to JSON string
		String json = response.toString();
		
		// Parse back
		try {
			response = JSONRPC2Response.parse(json);
		
		} catch (JSONRPC2ParseException e) {
			fail(e.getMessage());
		}
		
		
		// Verify parsed response members
		assertFalse(response.indicatesSuccess());
		err = response.getError();
		assertEquals(JSONRPC2Error.METHOD_NOT_FOUND.getCode(), err.getCode());
		assertEquals(JSONRPC2Error.METHOD_NOT_FOUND.getMessage(), err.getMessage());
		assertEquals(id, response.getID());
	}
	
	
	public void testParseRequestExceptionNull() {
	
		try {
			JSONRPC2Request.parse(null);
			fail("Failed to raise parse exception");
			
		} catch (JSONRPC2ParseException e) {
			// ok
			assertEquals(JSONRPC2ParseException.JSON, e.getCauseType());
		}
	}
	
	
	public void testParseNotificationExceptionNull() {
	
		try {
			JSONRPC2Notification.parse(null);
			fail("Failed to raise parse exception");
			
		} catch (JSONRPC2ParseException e) {
			// ok
			assertEquals(JSONRPC2ParseException.JSON, e.getCauseType());
		}
	}
	
	
	public void testParseResponseExceptionNull() {
	
		try {
			JSONRPC2Response.parse(null);
			fail("Failed to raise parse exception");
			
		} catch (JSONRPC2ParseException e) {
			// ok
			assertEquals(JSONRPC2ParseException.JSON, e.getCauseType());
		}
	}
	
	
	public void testParseRequestExceptionEmpty() {
	
		try {
			JSONRPC2Request.parse("");
			fail("Failed to raise parse exception");
			
		} catch (JSONRPC2ParseException e) {
			// ok
			assertEquals(JSONRPC2ParseException.JSON, e.getCauseType());
		}
	}
	
	
	public void testParseNotificationExceptionEmpty() {
	
		try {
			JSONRPC2Notification.parse("");
			fail("Failed to raise parse exception");
			
		} catch (JSONRPC2ParseException e) {
			// ok
			assertEquals(JSONRPC2ParseException.JSON, e.getCauseType());
		}
	}
	
	
	public void testParseResponseExceptionEmpty() {
	
		try {
			JSONRPC2Response.parse("");
			fail("Failed to raise parse exception");
			
		} catch (JSONRPC2ParseException e) {
			// ok
			assertEquals(JSONRPC2ParseException.JSON, e.getCauseType());
		}
	}
	
	
	public void testParseNotificationIgnoreVersion() {
	
		String json = "{ \"method\":\"test\", \"params\":[1,2,3] }";
		
		try {
			boolean preserveOrder = false;
			boolean ignoreVersion = true;
			JSONRPC2Notification.parse(json, preserveOrder, ignoreVersion);
		} catch (JSONRPC2ParseException e) {
			fail(e.getMessage());
		}
	}
	
	
	public void testParseStrictNotification() {
	
		String json = "{ \"method\":\"test\", \"params\":[1,2,3] }";
		
		try {
			boolean preserveOrder = false;
			boolean ignoreVersion = false;
			JSONRPC2Notification.parse(json, preserveOrder, ignoreVersion);
			fail("Failed to raise parse exception");
		} catch (JSONRPC2ParseException e) {
			// ok
			assertEquals(JSONRPC2ParseException.PROTOCOL, e.getCauseType());
		}
	}
	
	
	public void testParseRequestIgnoreVersion() {
	
		String json = "{ \"method\":\"test\", \"params\":[1,2,3], \"id\":100 }";
		
		try {
			boolean preserveOrder = false;
			boolean ignoreVersion = true;
			JSONRPC2Request.parse(json, preserveOrder, ignoreVersion);
		} catch (JSONRPC2ParseException e) {
			fail(e.getMessage());
		}
	}
	
	
	public void testParseStrictRequest() {
	
		String json = "{ \"method\":\"test\", \"params\":[1,2,3], \"id\":100 }";
		
		try {
			boolean preserveOrder = false;
			boolean ignoreVersion = false;
			JSONRPC2Request.parse(json, preserveOrder, ignoreVersion);
			fail("Failed to raise parse exception");
		} catch (JSONRPC2ParseException e) {
			// ok
			assertEquals(JSONRPC2ParseException.PROTOCOL, e.getCauseType());
		}
	}
	
	
	public void testParseResponseIgnoreVersion() {
	
		String json = "{ \"result\":\"OK\", \"id\":100 }";
		
		try {
			boolean preserveOrder = false;
			boolean ignoreVersion = true;
			JSONRPC2Response.parse(json, preserveOrder, ignoreVersion);
		} catch (JSONRPC2ParseException e) {
			fail(e.getMessage());
		}
	}
	
	
	public void testParseStrictResponse() {
	
		String json = "{ \"result\":\"OK\", \"id\":100 }";
		
		try {
			boolean preserveOrder = false;
			boolean ignoreVersion = false;
			JSONRPC2Response.parse(json, preserveOrder, ignoreVersion);
			fail("Failed to raise parse exception");
		} catch (JSONRPC2ParseException e) {
			// ok
			assertEquals(JSONRPC2ParseException.PROTOCOL, e.getCauseType());
		}
	}
	
	
	public void testPreserveOrder() {
	
		Map params = new LinkedHashMap();
		params.put("one", 1);
		params.put("two", 2);
		params.put("three", 3);
		params.put("four", null);
		
		JSONRPC2Request req = new JSONRPC2Request("test", params, 0);
		
		try {
			req = JSONRPC2Request.parse(req.toString(), true);
			
		} catch (JSONRPC2ParseException e) {
			fail(e.getMessage() + ": " + req.toString());
		}
		
		Iterator it = ((Map)req.getParams()).keySet().iterator();
	
		assertEquals("one", (String)it.next());
		assertEquals("two", (String)it.next());
		assertEquals("three", (String)it.next());
		assertEquals("four", (String)it.next());
	}
	
	
	public void testParseBadJson() {
	
		String json = "a b c d e";
		
		try {
			JSONRPC2Message m = JSONRPC2Message.parse(json);
			fail("Failed to raise parse exception");
			
		} catch (JSONRPC2ParseException e) {
			
			// ok
			assertEquals("Invalid JSON", e.getMessage());
			assertEquals(JSONRPC2ParseException.JSON, e.getCauseType());
		}
	}
	
	
	public void testParserSettings() {
	
		JSONRPC2Parser parser = new JSONRPC2Parser();
		
		assertEquals(false, parser.preservesOrder());
		assertEquals(false, parser.ignoresVersion());
		
		parser.preserveOrder(true);
		parser.ignoreVersion(true);
	
		assertEquals(true, parser.preservesOrder());
		assertEquals(true, parser.ignoresVersion());
	}
	
	
	public void testJsonRpc2ErrorEqualityTrue1() {
	
		JSONRPC2Error err1 = new JSONRPC2Error(100, "Custom error");
		
		JSONRPC2Error err2 = new JSONRPC2Error(100, "Custom error");
		
		assertTrue(err1.equals(err2));
	}
	
	
	public void testJsonRpc2ErrorEqualityTrue2() {
	
		// only the error codes must be compared
		
		JSONRPC2Error err1 = new JSONRPC2Error(100, "ABC");
		
		JSONRPC2Error err2 = new JSONRPC2Error(100, "XYZ");
		
		assertTrue(err1.equals(err2));
	}
	
	
	public void testJsonRpc2ErrorEqualityFalse() {
	
		// only the error codes must be compared
		
		JSONRPC2Error err1 = new JSONRPC2Error(100, "ABC");
		
		JSONRPC2Error err2 = new JSONRPC2Error(101, "ABC");
		
		assertFalse(err1.equals(err2));
	}
}
