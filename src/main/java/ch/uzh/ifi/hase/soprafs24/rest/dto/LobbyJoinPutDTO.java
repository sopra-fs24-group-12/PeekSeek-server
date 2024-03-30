package ch.uzh.ifi.hase.soprafs24.rest.dto;

public class LobbyJoinPutDTO {
    private String username;
    private String lobbyPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLobbyPassword() {
        return lobbyPassword;
    }

    public void setLobbyPassword(String lobbyPassword) {
        this.lobbyPassword = lobbyPassword;
    }
}
