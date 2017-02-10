package com.cjy.code.socket;

import java.io.IOException;
import java.io.OutputStream;

public interface Framer {
    void feameMsg(byte[] message, OutputStream out) throws IOException;

    byte[] nextMsg() throws IOException;
}
