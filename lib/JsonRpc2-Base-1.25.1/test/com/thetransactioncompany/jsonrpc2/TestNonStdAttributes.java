package com.thetransactioncompany.jsonrpc2;


import java.util.*;

import net.minidev.json.*;
import net.minidev.json.parser.*;

import junit.framework.*;


/**
 * JUnit tests for appending and parsing non-standard attributes to JSON-RPC 2.0
 * messages.
 *
 * @author Vladimir Dzhuvinov
 * @version 1.25.1.1 (2011-08-04)
 */
public class TestNonStdAttributes extends TestCase {

	
	private final JSONParser parser = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
	
	
	public TestNonStdAttributes(String name) {
	
		super(name);
	}

	
	public void testAppendRequest() {
		
		JSONRPC2Request req = new JSONRPC2Request("do.test", 0);
		
		req.appendNonStdAttribute("_debug", true);
		
		assertEquals(true, req.getNonStdAttribute("_debug"));
		
		Object o = null;
		
		try {
			o = parser.parse(req.toString());
			
		} catch (ParseException e) {
		
			fail(e.getMessage());
		}
		
		assertTrue(o instanceof Map);
		
		Map jsonObject = (Map)o;
		
		assertEquals(true, jsonObject.get("_debug"));
		
		try {
			JSONRPC2Parser parser = new JSONRPC2Parser();
			parser.parseNonStdAttributes(true);
			
			req = parser.parseJSONRPC2Request(req.toString());
		
		} catch (JSONRPC2ParseException e) {
		
			fail(e.getMessage());
		}
		
		assertEquals(true, req.getNonStdAttribute("_debug"));
	}
	
	
	public void testAppendNotification() {
		
		JSONRPC2Notification ntf = new JSONRPC2Notification("do.test");
		
		ntf.appendNonStdAttribute("_origin", "xyz");
		
		assertEquals("xyz", ntf.getNonStdAttribute("_origin"));
		
		Object o = null;
		
		try {
			o = parser.parse(ntf.toString());
			
		} catch (ParseException e) {
		
			fail(e.getMessage());
		}
		
		assertTrue(o instanceof Map);
		
		Map jsonObject = (Map)o;
		
		assertEquals("xyz", jsonObject.get("_origin"));
		
		try {
			JSONRPC2Parser parser = new JSONRPC2Parser();
			parser.parseNonStdAttributes(true);
			
			ntf = parser.parseJSONRPC2Notification(ntf.toString());
		
		} catch (JSONRPC2ParseException e) {
		
			fail(e.getMessage());
		}
		
		assertEquals("xyz", ntf.getNonStdAttribute("_origin"));
	}
	
	
	public void testAppendResponse() {
		
		long t = 1000;
		
		JSONRPC2Response resp = new JSONRPC2Response("success", 0);
		
		resp.appendNonStdAttribute("_procTime", t);
		
		assertEquals(t, resp.getNonStdAttribute("_procTime"));
		
		Object o = null;
		
		try {
			o = parser.parse(resp.toString());
			
		} catch (ParseException e) {
		
			fail(e.getMessage());
		}
		
		assertTrue(o instanceof Map);
		
		Map jsonObject = (Map)o;
		
		assertEquals(t, jsonObject.get("_procTime"));
		
		
		try {
			JSONRPC2Parser parser = new JSONRPC2Parser();
			parser.parseNonStdAttributes(true);
			
			resp = parser.parseJSONRPC2Response(resp.toString());
		
		} catch (JSONRPC2ParseException e) {
		
			fail(e.getMessage());
		}
		
		assertEquals(t, resp.getNonStdAttribute("_procTime"));
	}
}
