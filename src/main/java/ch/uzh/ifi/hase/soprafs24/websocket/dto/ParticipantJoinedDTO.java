package ch.uzh.ifi.hase.soprafs24.websocket.dto;

import java.util.List;

public class ParticipantJoinedDTO {
    private String status = "joined";

    public List<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }

    private List<String> usernames;

    public ParticipantJoinedDTO(List<String> usernames) {
        this.usernames = usernames;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
