package com.enat.sharemanagement.attendance;

import com.enat.sharemanagement.agenda.AgendaVote;
import com.enat.sharemanagement.shareholder.Person;
import com.enat.sharemanagement.shareholder.Shareholder;
import com.enat.sharemanagement.utils.Auditable;
import com.enat.sharemanagement.vote.Candidate;
import com.enat.sharemanagement.vote.CandidateVote;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;

@Entity(name = "attendance")
@Data
public class Attendance extends Person implements Serializable {
    @Id
    private long id;
    //    @OneToOne
//    private Shareholder shareholder;
    private BigDecimal paidSubscription;
    private BigDecimal unPaidSubscription;
    private boolean attend;
    private boolean voted;
    private boolean canAttend;
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "shareholder_vote",
//            joinColumns = @JoinColumn(name = "shareholder_id"),
//            inverseJoinColumns = @JoinColumn(name = "candidate_id")
//
//    )
//    private List<Candidate> candidates;
    @OneToMany(mappedBy="attendance")
    private List<CandidateVote> votes;
    private String budgetYear;
    private BigDecimal noOfShares;

    @ManyToOne
    private Delegate delegate;

    @OneToMany(mappedBy = "attendance")
    Set<AgendaVote> attendance;


}
