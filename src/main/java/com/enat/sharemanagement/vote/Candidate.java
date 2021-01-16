package com.enat.sharemanagement.vote;

import com.enat.sharemanagement.attendance.Attendance;
import com.enat.sharemanagement.shareholder.Shareholder;
import com.enat.sharemanagement.utils.Auditable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "candidates")
@Data
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(exclude = {"shareholders"})
public class Candidate extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private BigDecimal totalVotes;
    @ManyToMany(mappedBy = "candidates")
    @JsonBackReference
    Set<Attendance> attendance;


}
