package ch.uzh.ifi.hase.soprafs24.websocket.dto;

import java.util.List;

public class ParticipantJoinedDTO {
    private String status = "joined";
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }

    private List<String> usernames;

    public ParticipantJoinedDTO(List<String> usernames, String username) {
        this.usernames = usernames;
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
