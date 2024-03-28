package ch.uzh.ifi.hase.soprafs24.rest.dto;

import ch.uzh.ifi.hase.soprafs24.entity.Participant;
import ch.uzh.ifi.hase.soprafs24.rest.mapper.DTOMapper;

import java.util.List;

public class LobbyGetDTO {
    private Long id;
    private String name;

    public List<Participant> getParticipants() {
        return participants;
    }

    private List<Participant> participants;

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
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

}
