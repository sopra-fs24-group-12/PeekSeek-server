package ch.uzh.ifi.hase.soprafs24.websocket.dto;

public class StartVotingDTO {
    private String status = "voting";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
