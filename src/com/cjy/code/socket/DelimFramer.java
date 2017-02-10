package com.cjy.code.socket;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DelimFramer implements Framer {

    private InputStream       in;
    private static final byte DELIMITER = '\n';

    public DelimFramer(InputStream in) {
        this.in = in;
    }

    @Override
    public void feameMsg(byte[] message, OutputStream out) throws IOException {
        for (byte b : message) {
            if (b == DELIMITER) {
                throw new IOException("Message contains delimiter");
            }
        }

        out.write(message);
        out.write(DELIMITER);
        out.flush();
    }

    @Override
    public byte[] nextMsg() throws IOException {
        ByteArrayOutputStream messagebuffer = new ByteArrayOutputStream();
        int nextByte;

        while ((nextByte = in.read()) != DELIMITER) {
            if (nextByte == -1) {
                if (messagebuffer.size() == 0) {
                    return null;
                } else {
                    throw new EOFException("Non-nmpty");
                }
            }
            messagebuffer.write(nextByte);
        }
        return messagebuffer.toByteArray();
    }

}
