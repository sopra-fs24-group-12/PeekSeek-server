package ch.uzh.ifi.hase.soprafs24.entity;

import ch.uzh.ifi.hase.soprafs24.repository.GameRepository;

import java.util.*;


public class Lobby {
    private Long id;
    private String name;
    private String password;
    private Integer roundDurationSeconds = 62;
    private String gameLocation;
    private GeoCodingData gameLocationCoordinates;
    private Integer maxParticipants = 6;
    private Integer joinedParticipants = 0;
    private List<String> quests;
    private Boolean reUsed = false;
    private Long adminId;
    private Boolean passwordProtected = false;
    private String adminUsername;
    private Map<String, Participant> participants = new HashMap<>();
    private List<String> usernames = new ArrayList<>();
    private static Long id_count = 1L;
    private Map<String, Long> lastActivityTimes = new HashMap<>();

    public Lobby() {
        this.id = id_count++;
    }

    public void resetLobby() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                participants.clear();
                usernames.clear();
                lastActivityTimes.clear();
                setAdminUsername(null);
                setReUsed(true);
                setJoinedParticipants(0);
                timer.cancel();
            }
        }, 5000);
    }

    public void addParticipant(Participant participant) {
        String token = participant.getToken();
        usernames.add(participant.getUsername());
        participants.put(token, participant);
        lastActivityTimes.put(token, System.currentTimeMillis());
        joinedParticipants++;
    }

    public void removeParticipant(String token) {
        String username = participants.get(token).getUsername();
        participants.remove(token);
        usernames.remove(username);
        lastActivityTimes.remove(token);
        joinedParticipants--;
    }

    public void updateActivityTime(String token) {
        lastActivityTimes.put(token, System.currentTimeMillis());
    }

    public List<String> removeInactiveParticipants(long timeout) {
        long currentTime = System.currentTimeMillis();
        List<String> inactiveParticipants = new ArrayList<>();
        for (Map.Entry<String, Long> entry : lastActivityTimes.entrySet()) {
            if (currentTime - entry.getValue() > timeout) {
                inactiveParticipants.add(entry.getKey());
            }
        }

        return inactiveParticipants;
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
        this.roundDurationSeconds = roundDurationSeconds + 2;
    }

    public String getGameLocation() {
        return gameLocation;
    }

    public void setGameLocation(String gameLocation) {
        this.gameLocation = gameLocation;
    }

    public GeoCodingData getGameLocationCoordinates() {
        return gameLocationCoordinates;
    }

    public void setGameLocationCoordinates(GeoCodingData gameLocationCoordinates) {
        this.gameLocationCoordinates = gameLocationCoordinates;
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

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public Boolean getPasswordProtected() {
        return passwordProtected;
    }

    public void setPasswordProtected(Boolean passwordProtected) {
        this.passwordProtected = passwordProtected;
    }
}
