package jms.stomp.tool;

import java.io.IOException;

import jms.stomp.Istomp.Message;
import jms.stomp.Istomp.TextMessage;

public abstract class BroadcastReciver extends ConsumerTool {

	protected String url;
	protected String topic;
	protected StayingProducer producerTool;

	protected BroadcastReciver(String url, String topic) {

		this.topic = topic;
		this.url = url;
		setUrl(this.url);
		setTopic(this.topic);

	}

	@Override
	public void start() {

		super.start();

		producerTool = new StayingProducer();
		producerTool.setUrl(this.url);
		producerTool.setTopic(this.topic);
		producerTool.setPatchSliceSzie(1);
		producerTool.start();

	}

	@Override
	public void onMessage(Message message) {

		super.onMessage(message);
		onRevieve((TextMessage) message);
	}

	public void broadCast(String message) throws IOException {

		producerTool.publicMessage(message);
	}

	protected abstract void onRevieve(TextMessage message);

	public void close() {

		super.close();
		producerTool.close();

	}

}
