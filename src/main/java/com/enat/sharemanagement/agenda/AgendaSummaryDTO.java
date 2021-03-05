package com.enat.sharemanagement.agenda;

import lombok.Data;

@Data
public class AgendaSummaryDTO {
    //    private AgendaDTO agenda;
    private long agendaId;
    private String title;
    private long yes;
    private long no;
    private long silent;
}
