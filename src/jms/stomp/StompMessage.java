package jms.stomp;

import java.util.Hashtable;
import java.util.Map;

import jms.stomp.Istomp.Message;

public class StompMessage implements Message {
	@SuppressWarnings("unused")
	private int priority;

	private Map<String, String> propertyMap;
	private long timestamp;

	StompMessage() {

		propertyMap = new Hashtable<String, String>();

	}

	@Override
	public String getStringProperty(String name) {

		return propertyMap.get(name);

	}

	@Override
	public boolean propertyExists(String name) {

		if (propertyMap.get(name) == null) {
			return false;
		}
		return true;
	}

	@Override
	public void setJMSPriority(int priority) {

		this.priority = priority;

	}

	@Override
	public void setStringProperty(String name, String value) {

		this.propertyMap.put(name, value);

	}

	@Override
	public long getJMSTimestamp() {

		return 0;
	}

}
