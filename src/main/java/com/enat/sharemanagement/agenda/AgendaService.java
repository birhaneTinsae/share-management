package com.enat.sharemanagement.agenda;

import com.enat.sharemanagement.attendance.Attendance;
import com.enat.sharemanagement.attendance.AttendanceService;
import com.enat.sharemanagement.exceptions.EntityNotFoundException;
import com.enat.sharemanagement.report.SimpleReportExporter;
import com.enat.sharemanagement.report.SimpleReportFiller;
import com.enat.sharemanagement.utils.ApplicationProps;
import com.enat.sharemanagement.utils.Common;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.xml.JRPrintXmlLoader;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.actuate.liquibase.LiquibaseEndpoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import static com.enat.sharemanagement.utils.Util.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class AgendaService implements Common<Agenda, Agenda, Long> {
    private final AgendaRepository agendaRepository;
    private final AttendanceService attendanceService;
    private final AgendaVoteRepository agendaVoteRepository;
    private final SimpleReportFiller reportFiller;
    private final SimpleReportExporter reportExporter;
    private final ApplicationProps applicationProps;

    @Override
    public Agenda store(@Valid Agenda agenda) {
        return agendaRepository.save(agenda);
    }

    @Override
    public Iterable<Agenda> store(List<@Valid Agenda> t) {
        return agendaRepository.saveAll(t);
    }

    @Override
    public Agenda show(Long id) {
        return agendaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Agenda.class, "Id", String.valueOf(id)));
    }

    @Override
    public Agenda update(Long id, @Valid Agenda agenda) {
        Agenda a = show(id);
        BeanUtils.copyProperties(agenda,a,getNullPropertyNames(agenda));
        return agendaRepository.save(a);
    }

    @Override
    public boolean delete(Long id) {
        agendaRepository.deleteById(id);
        return agendaRepository.existsById(id);
    }

    @Override
    public Page<Agenda> getAll(Pageable pageable) {
        return agendaRepository.findAll(pageable);
    }

    public AgendaVote vote(Long agendaId, Long attendanceId, int vote) {
        Agenda agenda = show(agendaId);
        Attendance attendance = attendanceService.show(attendanceId);
        if (!attendance.isAttend()) {
            throw new IllegalStateException("Only attended Shareholder can make vote.");
        }
        var voteKey = new AgendaVoteKey(agendaId, attendanceId);
        var agendaVote = new AgendaVote(voteKey, agenda, attendance, vote);
       return agendaVoteRepository.save(agendaVote);
    }

    public List<AgendaSummary> getAgendaSummery() {
        return agendaRepository.getAgendaSummary()
                .orElse(List.of());
    }

    public JasperPrint exportReport() {
        reportFiller.setReportFileName("Agenda.jrxml");
        HashMap<String, Object> parameters = new HashMap<>();
        reportFiller.setParameters(parameters);
        reportFiller.prepareReport();
        return reportFiller.getJasperPrint();
    }

    public void exportReportToXlxs(OutputStream outputStream) {
        reportExporter.setJasperPrint(exportReport());
        reportExporter.exportToXlsx(applicationProps.getAuthor(),outputStream);
    }

    public void exportReportToCsv(OutputStream outputStream) {
        reportExporter.setJasperPrint(exportReport());
        reportExporter.exportToCsv(outputStream);
    }

    public void exportReportToHtml(OutputStream outputStream) {
        reportExporter.setJasperPrint(exportReport());
        reportExporter.exportToHtml(outputStream);
    }

}
