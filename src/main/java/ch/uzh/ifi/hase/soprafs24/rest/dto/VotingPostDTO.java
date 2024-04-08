package ch.uzh.ifi.hase.soprafs24.rest.dto;

import java.util.HashMap;

public class VotingPostDTO {
  // all submission are transferred from the client. Depending on the vote of the user, the winning submission is determined
  // disqualified submissions are transferrde as well, but include a banVote
  private HashMap<Long, String> votes;  // key: submissionId, value: vote; vote can either be winning or ban

  public HashMap<Long, String> getVotes() {
    return votes;
  }

  public void setVotes(HashMap<Long, String> votes) {
    this.votes = votes;
  }
}
