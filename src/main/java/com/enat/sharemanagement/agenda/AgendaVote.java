package com.enat.sharemanagement.agenda;

import com.enat.sharemanagement.attendance.Attendance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "agenda_vote")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgendaVote {
    @EmbeddedId
    private AgendaVoteKey id;

    @ManyToOne
    @MapsId("agendaId")
    @JoinColumn(name = "agenda_id")
    private Agenda agenda;

    @ManyToOne
    @MapsId("attendanceId")
    @JoinColumn(name = "attendance_id")
    private Attendance attendance;

    private int vote;

}



