package ch.uzh.ifi.hase.soprafs24.websocket.dto;

public class GameStartedDTO {
    private String status = "started";
    private Long gameId;

    public GameStartedDTO(Long gameId) {
        this.gameId = gameId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
}
