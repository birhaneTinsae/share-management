package com.enat.sharemanagement.agenda;

import com.enat.sharemanagement.utils.Auditable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity(name="agendas")
@NoArgsConstructor
@AllArgsConstructor
public class Agenda extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @OneToMany(mappedBy = "agenda")
    Set<AgendaVote> votes;

    public Agenda(Long id) {
        this.id = id;
    }

}
