package com.cjy.code.laoa.chapter04.socket.client.sender;

import java.io.IOException;

import com.cjy.code.laoa.chapter04.socket.SocketWrapper;

public interface Sendable {
	
	public byte getSendType();

	public void sendContent(SocketWrapper socketWrapper) throws IOException;
}
