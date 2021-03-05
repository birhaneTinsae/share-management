package com.enat.sharemanagement.agenda;

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
public class AgendaVoteKey implements Serializable {

    @Column(name = "agenda_id")
    private Long agendaId;

    @Column(name = "attendance_id")
    private Long attendanceId;

    // standard constructors, getters, and setters
    // hashcode and equals implementation
}