package ch.uzh.ifi.hase.soprafs24.rest.dto;

import java.util.Map;

public class VotingPostDTO {
  private Map<Long, String> votes;

  public Map<Long, String> getVotes() {
    return votes;
  }

  public void setVotes(Map<Long, String> votes) {
    this.votes = votes;
  }
}
