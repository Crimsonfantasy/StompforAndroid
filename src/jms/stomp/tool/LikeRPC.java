package jms.stomp.tool;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;

import jms.stomp.Istomp.TextMessage;

/**
 * LikeRPC design for demand which contains one require and one response.
 * 
 * @author yu-sheng
 * 
 */
public abstract class LikeRPC extends RequestAndReponse {

	private String id;
	JSONRPC2Request JSONRPC = null;

	protected LikeRPC(String id, String url, String topic) {

		super(url, topic);

		if (id == null || id.length() == 0) {
			throw new IllegalArgumentException(
					" must assign room id (length of id >0 and not null)");
		}
		this.id = id;
		setTopic(this.topic + "/" + id);

	}

	public void Call(String functionName, Object parameter) throws IOException {

		if (parameter instanceof Map) {
			JSONRPC = new JSONRPC2Request(functionName, (Map<?, ?>) parameter,
					id);
		} else if (parameter instanceof List)
			JSONRPC = new JSONRPC2Request(functionName, (List<?>) parameter, id);
		else {
			throw new IllegalArgumentException(
					"only map and list can be assign as parameter");
		}

		super.Request();

	}

	@Override
	protected String onReques() {

		return JSONRPC.toString();

	}

}
