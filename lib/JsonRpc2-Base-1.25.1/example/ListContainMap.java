import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import net.minidev.json.JSONArray;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;

	public static void main(String[] args) throws Exception {
		//
		// roomServer = new RoomServer();
		// roomServer.setTopic("A");
		// roomServer.schedual(0, 3);

		LinkedList<Hashtable<String, String>> test = new LinkedList<Hashtable<String, String>>();
		Hashtable<String, String> ht = new Hashtable<String, String>();
		ht.put("A", "1");

		Hashtable<String, String> ht2 = new Hashtable<String, String>();
		ht2.put("b", "2");

		test.push(ht);
		test.push(ht2);

		JSONRPC2Request request = new JSONRPC2Request("hello", test, 0);

		System.out.print(request.toJSON().toString());

		String recieve = request.toString();

		System.out.println(recieve);

		JSONRPC2Request request2 = JSONRPC2Request.parse(recieve);
		
		
		JSONArray ar = (JSONArray) request2.getParams();
		Iterator<Object> itr = ar.iterator();
		while (itr.hasNext()) {

			@SuppressWarnings("unchecked")
			Map<String ,String> m = (Map<String ,String>) itr.next();
			System.out.println(m.get("A"));
			System.out.println(m.get("b"));

		}

		
	}