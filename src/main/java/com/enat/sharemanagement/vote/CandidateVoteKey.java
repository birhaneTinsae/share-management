package com.enat.sharemanagement.vote;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateVoteKey implements Serializable {
    @Column(name ="candidate_id")
    Long candidateId;
    @Column(name ="attendance_id")
    Long attendanceId;
}
