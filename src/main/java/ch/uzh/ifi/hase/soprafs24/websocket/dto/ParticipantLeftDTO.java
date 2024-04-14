package ch.uzh.ifi.hase.soprafs24.websocket.dto;

public class ParticipantLeftDTO {
    private String status = "left";
    private String username;
    private String newAdmin;

    public ParticipantLeftDTO(String username) {
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

    public String getNewAdmin() {
        return newAdmin;
    }

    public void setNewAdmin(String newAdmin) {
        this.newAdmin = newAdmin;
    }
}
