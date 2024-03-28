package ch.uzh.ifi.hase.soprafs24.rest.dto;

import ch.uzh.ifi.hase.soprafs24.entity.Participant;
import ch.uzh.ifi.hase.soprafs24.rest.mapper.DTOMapper;

import java.util.List;

public class LobbyGetDTO {
    private Long id;
    private String name;
    private Integer maxParticipants;
    private Integer joinedParticipants;

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
}
