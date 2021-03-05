package com.enat.sharemanagement.report;

import com.enat.sharemanagement.agenda.AgendaService;
import com.enat.sharemanagement.attendance.AttendanceService;
import com.enat.sharemanagement.vote.CandidateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@RequestMapping("/reports")
@RestController
@RequiredArgsConstructor
@Log4j2
public class ReportController {
    private final CandidateService candidateService;
    private final AttendanceService attendanceService;
    private final AgendaService agendaService;

    @RequestMapping(value = "/candidates", produces = MediaType.APPLICATION_PDF_VALUE)
    public void candidatesPDFReport(HttpServletResponse response, @RequestParam int noOfSelects, @RequestParam int reserve) {
        try (OutputStream outputStream = response.getOutputStream()) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=".concat("candidate"));
            JasperExportManager.exportReportToPdfStream(candidateService.exportReport(noOfSelects, reserve), outputStream);
        } catch (IOException | JRException e) {
            log.fatal("Failed to generate report", e);
        }
    }
    @RequestMapping(value = "/candidates-voters", produces = MediaType.APPLICATION_PDF_VALUE)
    public void candidatesVotersPDFReport(HttpServletResponse response, @RequestParam int noOfSelects, @RequestParam int reserve) {
        try (OutputStream outputStream = response.getOutputStream()) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=".concat("candidate"));
            JasperExportManager.exportReportToPdfStream(candidateService.exportCandidateVotersReport(noOfSelects, reserve), outputStream);
        } catch (IOException | JRException e) {
            log.fatal("Failed to generate report", e);
        }
    }

    @RequestMapping(value = "/candidates/xlsx")
    public void candidatesXlxsReport(HttpServletResponse response, @RequestParam int noOfSelects, @RequestParam int reserve) {
        try (OutputStream outputStream = response.getOutputStream()) {
            response.setContentType("application/vnd-ms-excel");
            response.setHeader("Content-Disposition", "inline; filename=".concat("candidate"));
            candidateService.exportReportXlsx(noOfSelects, reserve, outputStream);
        } catch (IOException e) {
            log.fatal("Failed to generate report", e);
        }
    }

    @RequestMapping(value = "/candidates/csv")
    public void candidatesCsvReport(HttpServletResponse response, @RequestParam int noOfSelects, @RequestParam int reserve) {
        try (OutputStream outputStream = response.getOutputStream()) {
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "inline; filename=".concat("candidate"));
            candidateService.exportReportCsv(noOfSelects, reserve, outputStream);
        } catch (IOException e) {
            log.fatal("Failed to generate report", e);
        }
    }

    @RequestMapping(value = "/candidates/html")
    public void candidatesHtmlReport(HttpServletResponse response, @RequestParam int noOfSelects, @RequestParam int reserve) {
        try (OutputStream outputStream = response.getOutputStream()) {
            response.setContentType("text/html");
            response.setHeader("Content-Disposition", "inline; filename=".concat("candidate"));
            candidateService.exportReportHtml(noOfSelects, reserve, outputStream);
        } catch (IOException e) {
            log.fatal("Failed to generate report", e);
        }
    }

    @RequestMapping(value = "/attendance", produces = MediaType.APPLICATION_PDF_VALUE)
    public void attendancePDFReport(HttpServletResponse response, @RequestParam boolean attend) {
        try (OutputStream outputStream = response.getOutputStream()) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=".concat("attendance.pdf"));
            JasperExportManager.exportReportToPdfStream(attendanceService.exportReport(attend), outputStream);

        } catch (IOException | JRException e) {
            log.fatal("Failed to generate report", e);
        }
    }

    @RequestMapping(value = "/attendance/xlsx")
    public void attendanceXlxsReport(HttpServletResponse response, @RequestParam boolean attend) {
        try (OutputStream outputStream = response.getOutputStream()) {
            response.setContentType("application/vnd-ms-excel");
            response.setHeader("Content-Disposition", "inline; filename=".concat("attendance.xlsx"));
            attendanceService.exportReportToXlxs(attend, outputStream);
        } catch (IOException e) {
            log.fatal("Failed to generate attendance xlsx report", e);
        }
    }

    @RequestMapping(value = "/attendance/csv")
    public void attendanceCsvReport(HttpServletResponse response, @RequestParam boolean attend) {
        try (OutputStream outputStream = response.getOutputStream()) {
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "inline; filename=".concat("attendance.csv"));
            attendanceService.exportReportToCsv(attend, outputStream);
        } catch (IOException e) {
            log.fatal("Failed to generate attendance csv report", e);
        }
    }

    @RequestMapping(value = "/attendance/html")
    public void attendanceHtmlReport(HttpServletResponse response, @RequestParam boolean attend) {
        try (OutputStream outputStream = response.getOutputStream()) {
            response.setContentType("text/html");
            response.setHeader("Content-Disposition", "inline; filename=".concat("attendance.csv"));
            attendanceService.exportReportToHtml(attend, outputStream);
        } catch (IOException e) {
            log.fatal("Failed to generate attendance csv report", e);
        }
    }

    @RequestMapping(value = "/agenda", produces = MediaType.APPLICATION_PDF_VALUE)
    public void agendaPDFReport(HttpServletResponse response) {
        try (OutputStream outputStream = response.getOutputStream()) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=".concat("agenda.pdf"));
            JasperExportManager.exportReportToPdfStream(agendaService.exportReport(), outputStream);

        } catch (IOException | JRException e) {
            log.fatal("Failed to generate report", e);
        }
    }

    @RequestMapping(value = "/agenda/xlsx")
    public void agendaXlxsReport(HttpServletResponse response) {
        try (OutputStream outputStream = response.getOutputStream()) {
            response.setContentType("application/vnd-ms-excel");
            response.setHeader("Content-Disposition", "inline; filename=".concat("agenda.xlsx"));
            agendaService.exportReportToXlxs(outputStream);
        } catch (IOException e) {
            log.fatal("Failed to generate attendance xlsx report", e);
        }
    }

    @RequestMapping(value = "/agenda/csv")
    public void agendaCsvReport(HttpServletResponse response) {
        try (OutputStream outputStream = response.getOutputStream()) {
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "inline; filename=".concat("aganda.csv"));
            agendaService.exportReportToCsv(outputStream);
        } catch (IOException e) {
            log.fatal("Failed to generate attendance csv report", e);
        }
    }

    @RequestMapping(value = "/agenda/html")
    public void agendaHtmlReport(HttpServletResponse response) {
        try (OutputStream outputStream = response.getOutputStream()) {
            response.setContentType("text/html");
            response.setHeader("Content-Disposition", "inline; filename=".concat("agenda.html"));
            agendaService.exportReportToHtml(outputStream);
        } catch (IOException e) {
            log.fatal("Failed to generate attendance csv report", e);
        }
    }
}
