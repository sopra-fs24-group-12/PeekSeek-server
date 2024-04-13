package ch.uzh.ifi.hase.soprafs24.websocket.dto;

public class GameEndDTO {
    private String status = "game_over";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
