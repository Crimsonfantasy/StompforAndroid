package jms.stomp.tool;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public abstract class BroadcasClient {

	private Timer timer;
	private String topic = null;
	private BroadcaseServerTask broadcaseServerTask;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private String url = null;

	private class BroadcaseServerTask extends TimerTask {

		StayingProducer producerTool;

		public BroadcaseServerTask() {

			producerTool = new StayingProducer();
			producerTool.setUrl(getUrl());
			// producerTool.setClientID("????");
			producerTool.setTopic(getTopic());
			producerTool.setPatchSliceSzie(1);
			producerTool.start();

		}

		@SuppressWarnings("null")
		@Override
		public void run() {

			String message = onBroadCastMessage();
			if (message == null)
				return;
			if (message.length() != 0) {
				// System.out.println(message);
				try {
					producerTool.publicMessage(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

		@Override
		public boolean cancel() {
			super.cancel();
			free();
			return false;

		}

		void free() {

			producerTool.close();
		}

	}

	public BroadcasClient(String url, String topic) {

		setTopic(topic);
		setUrl(url);
		this.timer = new Timer();

	}

	public void setTopic(String topic) {

		this.topic = topic;
	}

	public String getTopic() {

		return this.topic;
	}

	/**
	 * 
	 * @param delay
	 *            : delay in seconds before task is to be executed.
	 * @param period
	 *            : time in seconds between successive task executions.
	 * @throws Exception
	 *             : server exception
	 */

	public void schedual(long delay, long period) throws Exception {

		if (topic == null) {

			throw new Exception(
					"please use setTopic() or setQueue to assign sending targer ");
		}
		delay = delay * 1000;
		period = period * 1000;
		broadcaseServerTask = new BroadcaseServerTask();
		timer.schedule(broadcaseServerTask, delay, period);

	}

	public void cancel() {

		broadcaseServerTask.cancel();
		timer.cancel();

	}

	public abstract String onBroadCastMessage();

}
