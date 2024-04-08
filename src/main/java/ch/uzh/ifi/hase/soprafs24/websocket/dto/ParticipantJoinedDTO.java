package ch.uzh.ifi.hase.soprafs24.websocket.dto;

public class ParticipantJoinedDTO {
    private String status = "joined";
    private String username;

    public ParticipantJoinedDTO(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
