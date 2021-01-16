package com.enat.sharemanagement.attendance;

import com.enat.sharemanagement.utils.Auditable;
import com.enat.sharemanagement.vote.Candidate;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity(name = "attendance")
@Data
public class Attendance extends Auditable implements Serializable {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private BigDecimal noOfShares;
    private boolean attend;
    private boolean voted;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "shareholder_vote",
            joinColumns = @JoinColumn(name = "shareholder_id"),
            inverseJoinColumns = @JoinColumn(name = "candidate_id")

    )
    private List<Candidate> candidates;

}
