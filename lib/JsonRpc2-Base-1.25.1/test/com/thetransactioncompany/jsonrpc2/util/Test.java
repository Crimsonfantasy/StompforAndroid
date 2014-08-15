package com.thetransactioncompany.jsonrpc2.util;


import java.util.*;

import junit.framework.*;

import com.thetransactioncompany.jsonrpc2.*;


/**
 * JUnit tests for the parameter retriever classes.
 *
 * @author Vladimir Dzhuvinov
 * @version 1.25.1 (2011-10-08)
 */
public class Test extends TestCase {

	
	public Test(String name) {
	
		super(name);
	}

	
	public void testPositionalParams() {
	
		// Create new request with positional parameters
		String method = "rpc.test";
		int id = 0;
		
		String param0 = "John Smith";
		int param1 = 25;
		boolean param2 = true;
		double param3 = 3.1415;
		Object param4 = null;
		
		List param5 = new LinkedList();
		int param5_0 = 0;
		int param5_1 = 1;
		int param5_2 = 2;
		int param5_3 = 3;
		int param5_4 = 4;
		param5.add(param5_0);
		param5.add(param5_1);
		param5.add(param5_2);
		param5.add(param5_3);
		param5.add(param5_4);
		
		String param6 = "one";
		String[] param6enums = {"one", "two", "three"};
		
		int nonExistingPosition = 7;
		
		List params = new LinkedList();
		params.add(param0);
		params.add(param1);
		params.add(param2);
		params.add(param3);
		params.add(param4);
		params.add(param5);
		params.add(param6);
		int size = params.size();
		
		// Create request from parsed back JSON string
		JSONRPC2Request request = new JSONRPC2Request(method, params, id);
		String json = request.toString();
		
		try {
			request = JSONRPC2Request.parse(json);
		
		} catch (JSONRPC2ParseException e) {
			fail("JSON-RPC 2.0 Request parse exception: " + e.getMessage());
		}
		
		// Create new positional params instance
		params = (List)request.getParams();
		PositionalParamsRetriever pp = new PositionalParamsRetriever(params);
		
		// Check params size
		assertEquals(size, params.size());
		
		// No exception must be thrown here
		try {
			for (int i=0; i < size; i++)
				pp.ensureParameter(i);
		
		} catch (JSONRPC2Error e) {
			fail("Missing positional parameter: " + e.getMessage());
		}
		
		// Force exception
		try {
			pp.ensureParameter(nonExistingPosition);
			fail("Failed to raise exception on non-existing position");
		} catch (JSONRPC2Error e) {
			// ok
		}
		
		
		// Check parameter test
		assertTrue(pp.hasParameter(0));
		assertTrue(pp.hasParameter(1));
		assertTrue(pp.hasParameter(2));
		assertTrue(pp.hasParameter(3));
		assertTrue(pp.hasParameter(4));
		assertTrue(pp.hasParameter(5));
		assertTrue(pp.hasParameter(6));
		assertFalse(pp.hasParameter(nonExistingPosition));
	
		// Compare parameters
		try {
			assertEquals(param0, pp.getString(0));
			assertEquals(param1, pp.getInt(1));
			assertEquals(param2, pp.getBoolean(2));
			assertEquals(param3, pp.getDouble(3));
			assertEquals(param4, pp.get(4));
			
			// List
			List ints = pp.getList(5);
			assertEquals(param5_0, ((Number)ints.get(0)).intValue());
			assertEquals(param5_1, ((Number)ints.get(1)).intValue());
			assertEquals(param5_2, ((Number)ints.get(2)).intValue());
			assertEquals(param5_3, ((Number)ints.get(3)).intValue());
			assertEquals(param5_4, ((Number)ints.get(4)).intValue());
			
			assertEquals(param6, pp.getEnumString(6, param6enums));
			
		} catch (JSONRPC2Error e) {
			fail("Comparison failed: " + e.getMessage());
		}
		
		// Test optional parameters
		try {
			
			assertEquals("abc", pp.getOptString(nonExistingPosition, "abc"));
			assertEquals(true, pp.getOptBoolean(nonExistingPosition, true));
			assertEquals(100, pp.getOptInt(nonExistingPosition, 100));
			assertEquals(3.1415, pp.getOptDouble(nonExistingPosition, 3.1415));
		
		} catch (JSONRPC2Error e) {
			fail("Invalid optional parameter: " + e.getMessage());
		}
	}


	public void testNamedParams() {
	
		// Create new request with named parameters
		String method = "rpc.test";
		int id = 0;
		
		String param0 = "John Smith";
		int param1 = 25;
		boolean param2 = true;
		double param3 = 3.1415;
		Object param4 = null;
		
		List param5 = new LinkedList();
		int param5_0 = 0;
		int param5_1 = 1;
		int param5_2 = 2;
		int param5_3 = 3;
		int param5_4 = 4;
		param5.add(param5_0);
		param5.add(param5_1);
		param5.add(param5_2);
		param5.add(param5_3);
		param5.add(param5_4);
		
		String param6 = "one";
		String[] param6enum = {"one", "two", "three"};
		
		Map params = new HashMap();
		params.put("param0", param0);
		params.put("param1", param1);
		params.put("param2", param2);
		params.put("param3", param3);
		params.put("param4", param4);
		params.put("param5", param5);
		params.put("param6", param6);
		
		
		int size = params.size();
		
		String badParamName = "non-existing-name";
		
		
		// Create request from parsed back JSON string
		JSONRPC2Request request = new JSONRPC2Request(method, params, id);
		String json = request.toString();
		
		try {
			request = JSONRPC2Request.parse(json);
		
		} catch (JSONRPC2ParseException e) {
			fail("JSON-RPC 2.0 Request parse exception: " + e.getMessage());
		}
		
		// Create new named params instance
		params = (Map)request.getParams();
		NamedParamsRetriever np = new NamedParamsRetriever(params);
		
		// Check params size
		assertEquals(size, np.size());
		
		// No exception must be thrown here
		try {
			for (int i=0; i < size; i++)
				np.ensureParameter("param" + i);
			
		} catch (JSONRPC2Error e) {
			fail(e.getMessage());
		}

		// Force exception
		try {
			np.ensureParameter(badParamName);
			
		} catch (JSONRPC2Error e) {
			assertTrue(true);
		}
		
		// Check parameter test
		assertTrue(np.hasParameter("param0"));
		assertTrue(np.hasParameter("param1"));
		assertTrue(np.hasParameter("param2"));
		assertTrue(np.hasParameter("param3"));
		assertTrue(np.hasParameter("param4"));
		assertTrue(np.hasParameter("param5"));
		assertTrue(np.hasParameter("param6"));
		assertFalse(np.hasParameter(badParamName));
		
		
		// Compare parameters
		try {
			assertEquals(param0, np.getString("param0"));
			assertEquals(param1, np.getInt("param1"));
			assertEquals(param2, np.getBoolean("param2"));
			assertEquals(param3, np.getDouble("param3"));
			assertEquals(param4, np.get("param4"));
			
			// List
			List ints = np.getList("param5");
			assertEquals(param5_0, ((Number)ints.get(0)).intValue());
			assertEquals(param5_1, ((Number)ints.get(1)).intValue());
			assertEquals(param5_2, ((Number)ints.get(2)).intValue());
			assertEquals(param5_3, ((Number)ints.get(3)).intValue());
			assertEquals(param5_4, ((Number)ints.get(4)).intValue());
			
			assertEquals(param6, np.getEnumString("param6", param6enum));
		
		} catch (JSONRPC2Error e) {
			fail(e.getMessage());
		}
		
		
		// Test optional parameters
		try {
			assertEquals("abc", np.getOptString(badParamName, "abc"));
			assertEquals(true, np.getOptBoolean(badParamName, true));
			assertEquals(100, np.getOptInt(badParamName, 100));
			assertEquals(3.1415, np.getOptDouble(badParamName, 3.1415));
		
		} catch (JSONRPC2Error e) {
			fail(e.getMessage());
		}
		
		// Test get names method
		String[] names = null;
		
		try {
			names = np.getNames();
			
		} catch (Exception e) {
			fail("Unexpected getNames() exception: " + e.getMessage());
		}
		
		assertEquals(size, names.length);
		
		for (int i=0; i < names.length; i++)
			assertEquals("param" + i, names[i]);
		
		
		// Test check names method
		try {
			np.ensureParameters(names);
			
		} catch (JSONRPC2Error e) {
			fail("Unexpected ensureParameters() exception: " + e.getMessage());
		}
		
		try {
			// modify the last name
			names[size-1] = "bad name";
			np.ensureParameters(names);
			
		} catch (JSONRPC2Error e) {
			assertTrue(true);
		}
		
		try {
			// name outside the list
			names = new String[size-1];
			
			for (int i=0; i< size-1; i++)
				names[i] = "param" + i;
			
			np.ensureParameters(names);
			
		} catch (JSONRPC2Error e) {
			assertTrue(true);
		}
	}
	
	
	public void testEnsureEnumString() {
	
		String[] enumValues = {"ONE", "TWO", "THREE"};
		
		boolean ignoreCase = false;
		
		// test observe case correct
		try {
			ignoreCase = false;
			ParamsRetriever.ensureEnumString("ONE", enumValues, ignoreCase);
			
		} catch (JSONRPC2Error e) {
			fail("Unexpected ensureEnumString() exception: " + e.getMessage());
		}
		
		
		// test observe case incorrect
		try {
			ignoreCase = false;
			ParamsRetriever.ensureEnumString("one", enumValues, ignoreCase);
			
		} catch (JSONRPC2Error e) {
			assertTrue(true);
		}
		
		// test ignore case correct 1
		try {
			ignoreCase = true;
			ParamsRetriever.ensureEnumString("ONE", enumValues, ignoreCase);
			
		} catch (JSONRPC2Error e) {
			fail("Unexpected ensureEnumString() exception: " + e.getMessage());
		}
		
		// test ignore case correct 2
		try {
			ignoreCase = true;
			ParamsRetriever.ensureEnumString("one", enumValues, ignoreCase);
			
		} catch (JSONRPC2Error e) {
			fail("Unexpected ensureEnumString() exception: " + e.getMessage());
		}
		
		// test ignore case incorrect
		try {
			ignoreCase = true;
			ParamsRetriever.ensureEnumString("four", enumValues, ignoreCase);
			
		} catch (JSONRPC2Error e) {
			assertTrue(true);
		}
	}
	
	
	public void testEnsureEnumString2() {
	
		// test observe case correct
		
		boolean ignoreCase = false;
		
		try {
			ParamsRetriever.ensureEnumString("MONDAY", TestEnumDay.class, ignoreCase);
		
		} catch (JSONRPC2Error e) {
			fail("Unexpected ensureEnumString() exception: " + e.getMessage());
		}
		
		
		// test observe case incorrect
		
		try {
			ParamsRetriever.ensureEnumString("SOMEDAY", TestEnumDay.class, ignoreCase);
			fail("Failed to raise exception");
			
		} catch (JSONRPC2Error e) {
			// ok
		}
		
		
		// test ignore case correct
		
		ignoreCase = true;
		
		try {
			ParamsRetriever.ensureEnumString("monday", TestEnumDay.class, ignoreCase);
		
		} catch (JSONRPC2Error e) {
			fail("Unexpected ensureEnumString() exception: " + e.getMessage());
		}
		
		// test observe case incorrect
		
		try {
			ParamsRetriever.ensureEnumString("SOMEDAY", TestEnumDay.class, ignoreCase);
			fail("Failed to raise exception");
			
		} catch (JSONRPC2Error e) {
			// ok
		}
	}
	
	
	public void testGetEnumPositional() {
	
		List l = new LinkedList();
		l.add("MONDAY");
		l.add("tuesday");
		l.add("SOMEDAY");
		
		PositionalParamsRetriever r = new PositionalParamsRetriever(l);
		
		TestEnumDay day = null;
		
		try {
			day = r.getEnum(0, TestEnumDay.class);
		
		} catch (JSONRPC2Error e) {
			fail(e.getMessage());
		}
		
		assertEquals(day.toString(), "MONDAY");
		
		boolean ignoreCase = true;
		
		try {
			day = r.getEnum(1, TestEnumDay.class, ignoreCase);
		
		} catch (JSONRPC2Error e) {
			fail(e.getMessage());
		}
		
		assertEquals(day.toString(), "TUESDAY");
		
		try {
			day = r.getEnum(2, TestEnumDay.class);
			fail("Failed to raise exception");
			
		} catch (JSONRPC2Error e) {
			// ok
		}
	}
	
	
	public void testGetEnumNamed() {
	
		Map m = new HashMap();
		m.put("Montag", "MONDAY");
		m.put("Diestag", "tuesday");
		m.put("Eintag", "SOMEDAY");
		
		NamedParamsRetriever r = new NamedParamsRetriever(m);
		
		TestEnumDay day = null;
		
		try {
			day = r.getEnum("Montag", TestEnumDay.class);
		
		} catch (JSONRPC2Error e) {
			fail(e.getMessage());
		}
		
		assertEquals(day.toString(), "MONDAY");
		
		boolean ignoreCase = true;
		
		try {
			day = r.getEnum("Diestag", TestEnumDay.class, ignoreCase);
		
		} catch (JSONRPC2Error e) {
			fail(e.getMessage());
		}
		
		assertEquals(day.toString(), "TUESDAY");
		
		try {
			day = r.getEnum("Eintag", TestEnumDay.class);
			fail("Failed to raise exception");
			
		} catch (JSONRPC2Error e) {
			// ok
		}
	}
	
	
	public void testEnsurePositional() {
	
		List l = new LinkedList();
		l.add("one");
		l.add("two");
		l.add("three");
		
		PositionalParamsRetriever r = new PositionalParamsRetriever(l);
		
		try {
			r.ensureParameter(0);
			r.ensureParameter(1);
			r.ensureParameter(2);
		} catch (JSONRPC2Error e) {
			fail(e.getMessage());
		}
		
		try {
			r.ensureParameter(3);
			fail("Failed to raise exception");
		} catch (JSONRPC2Error e) {
			// ok
		}
	}
	
	
	public void testEnsureNamed() {
	
		Map m = new HashMap();
		m.put("one", 1);
		m.put("two", 2);
		m.put("three", 3);
		
		NamedParamsRetriever r = new NamedParamsRetriever(m);
		
		try {
			r.ensureParameter("one");
			r.ensureParameter("two");
			r.ensureParameter("three");
		} catch (JSONRPC2Error e) {
			fail(e.getMessage());
		}
		
		try {
			r.ensureParameter("four");
			fail("Failed to raise exception");
		} catch (JSONRPC2Error e) {
			// ok
		}
	}
	
	
	public void testEnsurePositionalTyped() {
	
		List l = new LinkedList();
		l.add("one");
		l.add(2);
		l.add(null);
		
		PositionalParamsRetriever r = new PositionalParamsRetriever(l);
		
		try {
			r.ensureParameter(0, String.class);
			r.ensureParameter(1, Integer.class);
		} catch (JSONRPC2Error e) {
			fail(e.getMessage());
		}
		
		try {
			r.ensureParameter(2, Double.class);
			fail("Failed to raise exception");
		} catch (JSONRPC2Error e) {
			// ok
		}
	}
	
	
	public void testEnsureNamedTyped() {
	
		Map m = new HashMap();
		m.put("one", "eins");
		m.put("two", 2);
		m.put("three", null);
		
		NamedParamsRetriever r = new NamedParamsRetriever(m);
		
		try {
			r.ensureParameter("one", String.class);
			r.ensureParameter("two", Integer.class);
		} catch (JSONRPC2Error e) {
			fail(e.getMessage());
		}
		
		try {
			r.ensureParameter("three", Double.class);
			fail("Failed to raise exception");
		} catch (JSONRPC2Error e) {
			// ok
		}
	}
	
	
	public void testPositionalNull() {
	
		List l = new LinkedList();
		l.add(null);
		
		PositionalParamsRetriever r = new PositionalParamsRetriever(l);
		
		boolean allowNull = true;
		
		assertTrue(r.hasParameter(0));
		
		try {
			r.get(0, String.class, allowNull);
			r.getOpt(0, String.class, allowNull, "HELLO");
			r.getString(0, allowNull);
			r.getOptString(0, allowNull, "HELLO");
			r.getList(0, allowNull);
			r.getOptList(0, allowNull, new LinkedList());
			r.getMap(0, allowNull);
			r.getOptMap(0, allowNull, new HashMap());
		
		} catch (JSONRPC2Error e) {
			fail(e.getMessage());
		}
		
		allowNull = false;
		
		try {
			r.get(0, String.class, allowNull);
			fail("Failed to raise exception");
		} catch (JSONRPC2Error e) {
			// ok
		}
		
		try {
			r.getOpt(0, String.class, allowNull, "HELLO");
			fail("Failed to raise exception");
		} catch (JSONRPC2Error e) {
			// ok
		}
		
		try {
			r.getString(0, allowNull);
			fail("Failed to raise exception");
		} catch (JSONRPC2Error e) {
			// ok
		}
		
		try {
			r.getOptString(0, allowNull, "HELLO");
			fail("Failed to raise exception");
		} catch (JSONRPC2Error e) {
			// ok
		}
		
		try {
			r.getList(0, allowNull);
			fail("Failed to raise exception");
		} catch (JSONRPC2Error e) {
			// ok
		}
		
		try {
			r.getOptList(0, allowNull, new LinkedList());
			fail("Failed to raise exception");
		} catch (JSONRPC2Error e) {
			// ok
		}
		
		try {
			r.getMap(0, allowNull);
			fail("Failed to raise exception");
		} catch (JSONRPC2Error e) {
			// ok
		}
		
		try {
			r.getOptMap(0, allowNull, new HashMap());
			fail("Failed to raise exception");
		} catch (JSONRPC2Error e) {
			// ok
		}
	
	}
	
	
	
	public void testNamedNull() {
	
		Map m = new HashMap();
		m.put("one", null);
		
		NamedParamsRetriever r = new NamedParamsRetriever(m);
		
		boolean allowNull = true;
		
		assertTrue(r.hasParameter("one"));
		
		try {
			r.get("one", String.class, allowNull);
			r.getOpt("one", String.class, allowNull, "HELLO");
			r.getString("one", allowNull);
			r.getOptString("one", allowNull, "HELLO");
			r.getList("one", allowNull);
			r.getOptList("one", allowNull, new LinkedList());
			r.getMap("one", allowNull);
			r.getOptMap("one", allowNull, new HashMap());
		
		} catch (JSONRPC2Error e) {
			fail(e.getMessage());
		}
		
		allowNull = false;
		
		try {
			r.get("one", String.class, allowNull);
			fail("Failed to raise exception");
		} catch (JSONRPC2Error e) {
			// ok
		}
		
		try {
			r.getOpt("one", String.class, allowNull, "HELLO");
			fail("Failed to raise exception");
		} catch (JSONRPC2Error e) {
			// ok
		}
		
		try {
			r.getString("one", allowNull);
			fail("Failed to raise exception");
		} catch (JSONRPC2Error e) {
			// ok
		}
		
		try {
			r.getOptString("one", allowNull, "HELLO");
			fail("Failed to raise exception");
		} catch (JSONRPC2Error e) {
			// ok
		}
		
		try {
			r.getList("one", allowNull);
			fail("Failed to raise exception");
		} catch (JSONRPC2Error e) {
			// ok
		}
		
		try {
			r.getOptList("one", allowNull, new LinkedList());
			fail("Failed to raise exception");
		} catch (JSONRPC2Error e) {
			// ok
		}
		
		try {
			r.getMap("one", allowNull);
			fail("Failed to raise exception");
		} catch (JSONRPC2Error e) {
			// ok
		}
		
		try {
			r.getOptMap("one", allowNull, new HashMap());
			fail("Failed to raise exception");
		} catch (JSONRPC2Error e) {
			// ok
		}
	}
}
