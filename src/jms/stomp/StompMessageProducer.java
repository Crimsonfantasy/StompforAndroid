package jms.stomp;

import com.android.transport.stomp.StompCommand;

import fcu.selab.stomp.Stomp;

import jms.stomp.Istomp.DeliveryMode;
import jms.stomp.Istomp.Destination;
import jms.stomp.Istomp.Message;
import jms.stomp.Istomp.MessageProducer;
import jms.stomp.Istomp.TextMessage;

public class StompMessageProducer implements MessageProducer {

	private StompCommand stompCommand;
	private int deliveryMode = DeliveryMode.NON_PERSISTENT;;

	public StompMessageProducer(StompCommand stompCommand) {

		this.stompCommand = stompCommand;

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public int getDeliveryMode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Destination getDestination() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getDisableMessageID() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getDisableMessageTimestamp() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getTimeToLive() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setDeliveryMode(int deliveryMode) {

		if (this.deliveryMode != deliveryMode) {
			if (deliveryMode == DeliveryMode.NON_PERSISTENT) {
				stompCommand.removeHeader(Stomp.Headers.Send.PERSISTENT);
			} else {
				stompCommand
						.setHeader(Stomp.Headers.Send.PERSISTENT, "default");
			}
		}
		this.deliveryMode = deliveryMode;

	}

	@Override
	public void setDisableMessageID(boolean value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDisableMessageTimestamp(boolean value) {

	}

	@Override
	public void setPriority(int defaultPriority) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTimeToLive(long timeToLive) {
		// TODO Auto-generated method stub

	}

	@Override
	public void send(Message message) {

		TextMessage tm = (TextMessage) message;
		stompCommand.send(tm.getText());

	}

}
