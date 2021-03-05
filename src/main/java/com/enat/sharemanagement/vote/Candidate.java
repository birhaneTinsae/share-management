package com.enat.sharemanagement.vote;

import com.enat.sharemanagement.attendance.Attendance;
import com.enat.sharemanagement.shareholder.Shareholder;
import com.enat.sharemanagement.utils.Auditable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity(name = "candidates")
@Data
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(exclude = {"shareholders"})
public class Candidate extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne()
    private Attendance shareholder;
    private BigDecimal totalVotes;
//    @ManyToMany(mappedBy = "candidates")
//    @JsonBackReference
//    Set<Attendance> attendance;
    @OneToMany(mappedBy="candidate")
    private List<CandidateVote> candidateVotes;


}
