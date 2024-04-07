package ch.uzh.ifi.hase.soprafs24.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lobby {
    private Long id;
    private String name;
    private String password;
    private Integer roundDurationSeconds = 60;
    private String gameLocation = "Zürich";
    private Integer maxParticipants = 6; //TODO: don't hardcode
    private Integer joinedParticipants = 0;
    private List<String> quests;
    private Boolean reUsed = false;
    private Long adminId;
    private Map<String, Participant> participants = new HashMap<>();
    private List<String> usernames = new ArrayList<>();
    private static Long id_count = 1L;

    public Lobby() {
        this.id = id_count++;
    }

    public void addParticipant(Participant participant) {
        String token = participant.getToken();
        usernames.add(participant.getUsername());
        participants.put(token, participant);
        joinedParticipants++;
    }

    public void removeParticipant(String token) {
        String username = participants.get(token).getUsername();
        participants.remove(token);
        usernames.remove(username);
        joinedParticipants--;
    }

    public Participant getParticipantByToken(String token) {
        return participants.get(token);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRoundDurationSeconds() {
        return roundDurationSeconds;
    }

    public void setRoundDurationSeconds(Integer roundDurationSeconds) {
        this.roundDurationSeconds = roundDurationSeconds;
    }

    public String getGameLocation() {
        return gameLocation;
    }

    public void setGameLocation(String gameLocation) {
        this.gameLocation = gameLocation;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public Integer getJoinedParticipants() {
        return joinedParticipants;
    }

    public void setJoinedParticipants(Integer joinedParticipants) {
        this.joinedParticipants = joinedParticipants;
    }

    public List<String> getQuests() {
        return quests;
    }

    public void setQuests(List<String> quests) {
        this.quests = quests;
    }

    public Boolean getReUsed() {
        return reUsed;
    }

    public void setReUsed(Boolean reUsed) {
        this.reUsed = reUsed;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Map<String, Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(Map<String, Participant> participants) {
        this.participants = participants;
    }

    public List<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }
}
