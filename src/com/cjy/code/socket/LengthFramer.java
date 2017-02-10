package com.cjy.code.socket;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LengthFramer implements Framer {

    public static final int MAXMESSAGELENGTH = Integer.MAX_VALUE;
    public static final int BYTEMASK         = 0xff;
    public static final int SHORTMASK        = 0xffff;
    public static final int BYTESHIFT        = 0x8;

    private DataInputStream in;

    public LengthFramer(InputStream in) {
        this.in = new DataInputStream(in);
    }

    @Override
    public void feameMsg(byte[] message, OutputStream out) throws IOException {
        if (message.length > MAXMESSAGELENGTH) {
            throw new IOException("error 1");
        }

        out.write((message.length >> BYTESHIFT) & BYTEMASK);
        out.write(message.length & BYTEMASK);
        out.write(message);
        out.flush();
    }

    @Override
    public byte[] nextMsg() throws IOException {
        int length;
        try {
            length = in.readUnsignedShort();
        } catch (EOFException e) {
            System.err.println(e.getMessage());
            return null;
        }

        byte[] msg = new byte[length];
        in.readFully(msg);
        return msg;
    }

}
