package com.enat.sharemanagement.attendance.log;

import com.enat.sharemanagement.shareholder.Shareholder;
import com.enat.sharemanagement.utils.Auditable;
import com.enat.sharemanagement.vote.Candidate;
import com.enat.sharemanagement.vote.CandidateLog;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "attendance_log")
@Data
public class AttendanceLog extends Auditable implements Serializable {
    @Id
    private long id;
    @ManyToOne
    private Shareholder shareholder;
    private boolean attend;
    private boolean voted;
    private String budgetYear;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "shareholder_vote_log",
            joinColumns = @JoinColumn(name = "shareholder_id"),
            inverseJoinColumns = @JoinColumn(name = "candidate_id")

    )
    private List<CandidateLog> candidates;

}
