package com.cjy.code.socket.vote;

public class VoteMsg {

    private boolean         isInquiry;
    private boolean         isResponse;
    private int             candidateID;
    private long            voteCount;

    private String          remark;

    public static final int MAX_CANDIDATE_ID = 1000;

    public VoteMsg(boolean isResponse, boolean isInquiry, int candidateID, long voteCount)
            throws IllegalArgumentException {
        if (voteCount != 0 && !isResponse) {
            throw new IllegalArgumentException("cuole");
        }

        if (candidateID < 0 || candidateID > MAX_CANDIDATE_ID) {
            throw new IllegalArgumentException("youcuole");
        }
        if (voteCount < 0) {
            throw new IllegalArgumentException("cuole");
        }

        this.candidateID = candidateID;
        this.isResponse = isResponse;
        this.isInquiry = isInquiry;
        this.voteCount = voteCount;
    }

    public boolean isInquiry() {
        return isInquiry;
    }

    public void setInquiry(boolean isInquiry) {
        this.isInquiry = isInquiry;
    }

    public boolean isResponse() {
        return isResponse;
    }

    public void setResponse(boolean isResponse) {
        this.isResponse = isResponse;
    }

    public int getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(int candidateID) {
        if (candidateID < 0 || candidateID > MAX_CANDIDATE_ID) {
            throw new IllegalArgumentException("youcuole");
        }
        this.candidateID = candidateID;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(long voteCount) {
        if (voteCount != 0 && !isResponse) {
            throw new IllegalArgumentException("cuole");
        }

        if (voteCount < 0) {
            throw new IllegalArgumentException("cuole");
        }
        this.voteCount = voteCount;
    }

    @Override
    public String toString() {
        String res = (isInquiry ? "inquiry" : "vote") + "for candidate " + candidateID;
        if (isResponse) {
            res = "response to " + res + "who now has " + voteCount + " vote(s)";
        }
        return res;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
