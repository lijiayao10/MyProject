package com.cjy.code.socket.vote;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class VoteMsgBinCoder implements VoteMsgCoder {

    public static final int MAGIC           = 0X5400;
    public static final int MAGIC_MASK      = 0XFC00;
    public static final int MAGIC_SHIFT     = 8;
    public static final int RESPONSE_FLAG   = 0X0200;
    public static final int INQUIRE_FLAG    = 0X0100;

    public static final int MIN_WIRE_LENGTH = 4;
    public static final int MAX_WIRE_LENGTH = 16;

    @Override
    public byte[] toWire(VoteMsg msg) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(byteStream);

        short magicAndFlags = MAGIC;
        if (msg.isInquiry()) {
            magicAndFlags |= INQUIRE_FLAG;
        }
        if (msg.isResponse()) {
            magicAndFlags |= RESPONSE_FLAG;
        }
        out.writeShort(magicAndFlags);
        out.writeShort((short) msg.getCandidateID());
        if (msg.isResponse()) {
            out.writeLong(msg.getVoteCount());
        }
        out.flush();
        byte[] date = byteStream.toByteArray();

        return date;
    }

    @Override
    public VoteMsg fromWire(byte[] input) throws IOException {

        if (input.length < MIN_WIRE_LENGTH) {
            throw new IOException("cuole");
        }

        ByteArrayInputStream msgStream = new ByteArrayInputStream(input);
        DataInputStream in = new DataInputStream(msgStream);
        int magic = in.readShort();
        if ((magic & MAGIC_MASK) != MAGIC) {
            throw new IOException("cuole" + ((magic & MAGIC_MASK) >> MAGIC_SHIFT));
        }

        boolean resp = ((magic & RESPONSE_FLAG) != 0);
        boolean inq = ((magic & INQUIRE_FLAG) != 0);
        int candidateID = in.readShort();
        if (candidateID < 0 || candidateID > 1000) {
            throw new IOException("cuole 2");
        }
        long count = 0;
        if (resp) {
            count = in.readLong();
            if (count < 0) {
                throw new IOException("cuole 3");
            }
        }

        return new VoteMsg(resp, inq, candidateID, count);
    }

}
