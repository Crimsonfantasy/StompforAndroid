package jms.stomp.tool;

import java.io.IOException;

import jms.stomp.Istomp.Message;
import jms.stomp.Istomp.TextMessage;

/**
 * 
 * @author yu-sheng Based on JSON-RPC, the parameter of construction id
 *         respected to id of JSON-RPC...
 * 
 * 
 */
public abstract class RequestAndReponse extends ConsumerTool {

	StayingProducer producerTool;
	// private String id;
	protected String url;
	protected String topic;

	/**
	 * 
	 * @param topic
	 * @param url
	 * @param id
	 *            : a union id for server recognize, the best selecting is the
	 *            your account.
	 */
	protected RequestAndReponse(String url, String topic) {

		// if (id == null || id.length() == 0) {
		// throw new IllegalArgumentException(
		// " must assign room id (length of id >0 and not null)");
		// }
		// setTopic(this.topic + "/" + this.id);

		// this.id = id;
		this.topic = topic;
		this.url = url;
		setUrl(this.url);
		setTopic(this.topic);

	}

	abstract public void onResponse(TextMessage message);

	@Override
	public void onMessage(Message message) {

		super.onMessage(message);
		this.close();
		onResponse((TextMessage) message);
	}

	protected abstract String onReques();

	protected void Request() throws IOException {

		producerTool = new StayingProducer();
		producerTool.setUrl(this.url);
		producerTool.setTopic(this.topic);
		producerTool.setPatchSliceSzie(1);
		producerTool.start();

		String message = onReques();
		if (message == null) {

		} else if (message.length() == 0) {

		}

		producerTool.publicMessage(message);
		producerTool.close();

	}

	protected void close() {
		super.close();
		producerTool.close();

	}

}
