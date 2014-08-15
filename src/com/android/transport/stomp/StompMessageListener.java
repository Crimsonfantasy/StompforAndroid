package com.android.transport.stomp;

public interface StompMessageListener {

	void onReadHeader(String newMessage);

	void onReadMessage(String newMessage);

}
