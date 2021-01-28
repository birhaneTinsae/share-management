package com.enat.sharemanagement.agenda;

import lombok.Data;

import java.io.Serializable;

@Data
public class AgendaDTO implements Serializable {
    private Long id;
    private String title;
}
