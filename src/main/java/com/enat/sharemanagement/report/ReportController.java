package com.enat.sharemanagement.report;

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

    @RequestMapping(value="/candidates",produces= MediaType.APPLICATION_PDF_VALUE)
    public void candidatesPDFReport(HttpServletResponse response, @RequestParam int noOfSelects,@RequestParam int reserve){
        try (OutputStream outputStream = response.getOutputStream()) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=".concat("candidate"));
            JasperExportManager.exportReportToPdfStream(candidateService.exportReport(noOfSelects,reserve,"pdf"),outputStream);
        } catch (IOException | JRException e) {
          log.fatal("Failed to generate report",e);
        }
    }

    @RequestMapping(value="/attendance",produces= MediaType.APPLICATION_PDF_VALUE)
    public void attendancePDFReport(HttpServletResponse response,@RequestParam boolean attend,@RequestParam String format){
        try (OutputStream outputStream = response.getOutputStream()) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=".concat("attendance.pdf"));
            JasperExportManager.exportReportToPdfStream(attendanceService.exportReport(attend,"pdf"),outputStream);
//            switch (format) {
//                 case "pdf":
//                    JasperExportManager.exportReportToPdfStream(attendanceService.exportReport(attend,"pdf"),outputStream);
//                 case  "html":
//                     JasExportManager.exportReportTo
//             }
        } catch (IOException | JRException e) {
            log.fatal("Failed to generate report",e);
        }
    }
}
