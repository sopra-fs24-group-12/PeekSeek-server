package ch.uzh.ifi.hase.soprafs24.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Lobby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private Integer roundDurationSeconds = 60;
    private String gameLocation = "Zürich";
    private Integer maxParticipants = 6; //TODO: don't hardcode
    private Integer joinedParticipants = 0;

    @ElementCollection
    private List<String> quests;
    private Boolean reUsed = false;
    private Long adminId;
    @OneToMany(mappedBy = "lobby", cascade = CascadeType.ALL)
    private List<Participant> participants = new ArrayList<>();

    public void addParticipant(Participant participant) {
        joinedParticipants += 1;
        participant.setLobby(this.getId());
        this.participants.add(participant);
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

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Integer getJoinedParticipants() {
        return joinedParticipants;
    }

    public void setJoinedParticipants(Integer joinedParticipants) {
        this.joinedParticipants = joinedParticipants;
    }
}
