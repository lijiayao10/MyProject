package com.cjy.code.socket.vote;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class VoteMsgTextCoder implements VoteMsgCoder {

    /**
     * Wire Format "VOTEPROTO" <"v"|"i"> [<RESPFLAG>] <CANDIDATE> [<VOTECNT>]
     */

    public static final String MAGIC           = "voting";
    public static final String VOTESTR         = "v";
    public static final String INQSTR          = "i";
    public static final String RESPONSESTR     = "R";

    public static final String CHARSETNAME     = "US-ASCII";
    public static final String DELIMSTR        = " ";
    public static final int    MAX_WIRE_LENGTH = 2000;

    @Override
    public byte[] toWire(VoteMsg msg) throws IOException {
        String msgString = MAGIC + DELIMSTR + (msg.isInquiry() ? INQSTR : VOTESTR) + DELIMSTR
                + (msg.isResponse() ? RESPONSESTR + DELIMSTR : "")
                + Integer.toString(msg.getCandidateID()) + DELIMSTR
                + Long.toString(msg.getVoteCount());

        byte[] date = msgString.getBytes();
        return date;
    }

    @Override
    public VoteMsg fromWire(byte[] input) throws IOException {
        ByteArrayInputStream msgStream = new ByteArrayInputStream(input);
        Scanner s = new Scanner(new InputStreamReader(msgStream, CHARSETNAME));
        boolean isInquiry;
        boolean isResponse;
        int candidateID;
        long voteCount;
        String token;

        try {
            token = s.next();
            if (!token.equals(MAGIC)) {
                throw new IOException("cuole");
            }
            token = s.next();
            if (token.equals(VOTESTR)) {
                isInquiry = false;
            } else if (!token.equals(INQSTR)) {
                throw new IOException("cuole2");
            } else {
                isInquiry = true;
            }

            token = s.next();
            if (token.equals(RESPONSESTR)) {
                isResponse = true;
                token = s.next();
            } else {
                isResponse = false;
            }

            candidateID = Integer.parseInt(token);

            if (isResponse) {
                token = s.next();
                voteCount = Long.parseLong(token);
            } else {
                voteCount = 0;
            }

        } catch (IOException e) {
            throw e;
        }
        return new VoteMsg(isResponse, isInquiry, candidateID, voteCount);
    }

}
