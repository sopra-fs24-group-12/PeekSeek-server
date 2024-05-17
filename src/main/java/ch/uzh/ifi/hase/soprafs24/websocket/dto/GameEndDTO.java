package ch.uzh.ifi.hase.soprafs24.websocket.dto;

public class GameEndDTO {
    private String status = "game_over";
    private Long summaryId;

    public GameEndDTO(Long summaryId) {
        this.summaryId = summaryId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getSummaryId() {
        return summaryId;
    }

    public void setSummaryId(Long summaryId) {
        this.summaryId = summaryId;
    }
}
