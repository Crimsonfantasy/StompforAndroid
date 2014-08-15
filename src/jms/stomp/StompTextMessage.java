package jms.stomp;

import jms.stomp.Istomp.TextMessage;

public class StompTextMessage extends StompMessage implements TextMessage {

	private String messageContext;

	@Override
	public String getText() {

		return new String(messageContext);
	}

	@Override
	public void setText(String string) {

		this.messageContext = new String(string);

	}

}
