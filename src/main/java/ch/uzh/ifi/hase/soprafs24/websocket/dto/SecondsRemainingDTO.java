package ch.uzh.ifi.hase.soprafs24.websocket.dto;

public class SecondsRemainingDTO {
    private int secondsRemaining;

    public SecondsRemainingDTO(int secondsRemaining) {
        this.secondsRemaining = secondsRemaining;
    }

    public int getSecondsRemaining() {
        return secondsRemaining;
    }

    public void setSecondsRemaining(int secondsRemaining) {
        this.secondsRemaining = secondsRemaining;
    }
}
