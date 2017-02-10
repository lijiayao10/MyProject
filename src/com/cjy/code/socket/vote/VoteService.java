package com.cjy.code.socket.vote;

import java.util.HashMap;
import java.util.Map;

public class VoteService {

    private Map<Integer, Long> results = new HashMap<>();

    public VoteMsg handleRequest(VoteMsg msg) {
        if (msg.isResponse()) {
            return msg;
        }
        msg.setResponse(true);
        int candidateID = msg.getCandidateID();
        Long count = results.get(candidateID);
        if (count == null) {
            count = 0l;
        }
        if (!msg.isInquiry()) {
            results.put(candidateID, ++count);
        }
        msg.setVoteCount(count);
        return msg;
    }

}
