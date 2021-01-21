package com.enat.sharemanagement.vote;

import com.enat.sharemanagement.attendance.log.AttendanceLog;
import com.enat.sharemanagement.shareholder.Shareholder;
import com.enat.sharemanagement.utils.Auditable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Entity(name = "candidates_log")
@Data
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(exclude = {"shareholders"})
public class CandidateLog extends Auditable implements Serializable {
    @Id
    private long id;
//    @OneToOne()
//    private Shareholder shareholder;
    private BigDecimal totalVotes;
    @ManyToMany(mappedBy = "candidates")
    @JsonBackReference
    Set<AttendanceLog> attendance;


}
