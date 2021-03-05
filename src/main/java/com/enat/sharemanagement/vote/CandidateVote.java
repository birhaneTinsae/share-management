package com.enat.sharemanagement.vote;

import com.enat.sharemanagement.agenda.Agenda;
import com.enat.sharemanagement.agenda.AgendaVoteKey;
import com.enat.sharemanagement.attendance.Attendance;
import com.enat.sharemanagement.utils.Auditable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "candidate_vote")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateVote extends Auditable {
    @EmbeddedId
    private CandidateVoteKey id;

    @ManyToOne
    @MapsId("candidateId")
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @ManyToOne
    @MapsId("attendanceId")
    @JoinColumn(name = "attendance_id")
    private Attendance attendance;

    private BigDecimal vote;
}
