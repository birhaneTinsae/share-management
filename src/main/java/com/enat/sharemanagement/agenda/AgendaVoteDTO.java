package com.enat.sharemanagement.agenda;

import com.enat.sharemanagement.attendance.AttendanceDTO;
import lombok.Data;

import java.io.Serializable;

@Data
public class AgendaVoteDTO implements Serializable {
    private AgendaDTO agenda;
    private AttendanceDTO attendance;
    private int vote;
}
