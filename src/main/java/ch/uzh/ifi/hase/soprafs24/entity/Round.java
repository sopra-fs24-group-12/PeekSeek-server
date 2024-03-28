package ch.uzh.ifi.hase.soprafs24.entity;

import ch.uzh.ifi.hase.soprafs24.constant.RoundStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Round {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String quest;
    @OneToMany(mappedBy = "round", cascade = CascadeType.ALL)
    private List<Submission> submissions;
    @OneToOne
    @JoinColumn(name = "winning_submission_id")
    private Submission winningSubmission;
    private RoundStatus roundStatus;
    private int roundTime;
}