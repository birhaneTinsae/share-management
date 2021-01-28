package com.enat.sharemanagement.agenda;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgendaRepository extends PagingAndSortingRepository<Agenda,Long> {

//    @Query(value="select new com.enat.sharemanagement.agenda.AgendaSummary(a,(select count(vote)" +
//            " from agenda_vote where id.agendaId.id=av.id.agendaId.id and vote=1) as yes," +
//            "(select count(vote) from agenda_vote where id.agendaId.id=av.id.agendaId.id and vote=0) as no,(select count(vote) from agenda_vote where id.agendaId.id=av.id.agendaId.id and vote=2) as silent) from agenda_vote av left join agendas a on av.id.agendaId.id=a.id group by av.id.agendaId.id")
//
   @Query("select new com.enat.sharemanagement.agenda.AgendaSummary(av.id.agendaId,a.title,(select count(vote) from agenda_vote where id.agendaId=av.id.agendaId and vote=1) ,(select count(vote) from agenda_vote where id.agendaId=av.id.agendaId and vote=0) ,(select count(vote) from agenda_vote where id.agendaId=av.id.agendaId and vote=2))" +
           " from agenda_vote as av" +
           " left join agendas as a on av.id.agendaId=a.id" +
           " group by av.id.agendaId")
    Optional<List<AgendaSummary>> getAgendaSummary();
}
