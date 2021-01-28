package com.enat.sharemanagement.agenda;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendaSummary {
    private long agendaId;
    private String title;
    private long yes;
    private long no;
    private long silent;
}
