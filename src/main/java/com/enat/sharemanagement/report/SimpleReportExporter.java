package com.enat.sharemanagement.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import org.springframework.stereotype.Component;

import java.io.OutputStream;

@Component
@Log4j2
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleReportExporter {

    private JasperPrint jasperPrint;

//    public void exportToPdf(String fileName, String author) {
//
//        // print report to file
//        JRPdfExporter exporter = new JRPdfExporter();
//
//        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(fileName));
//
//        SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
//        reportConfig.setSizePageToContent(true);
//        reportConfig.setForceLineBreakPolicy(false);
//
//        SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
//        exportConfig.setMetadataAuthor(author);
//        exportConfig.setEncrypted(true);
//        exportConfig.setAllowedPermissionsHint("PRINTING");
//
//        exporter.setConfiguration(reportConfig);
//        exporter.setConfiguration(exportConfig);
//        try {
//            exporter.exportReport();
//        } catch (JRException ex) {
//            log.fatal("Failed to export pdf report",ex);
//        }
//    }
    public void exportToPdf(String fileName, String author) {


        // print report to file
        JRPdfExporter exporter = new JRPdfExporter();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(fileName));

        SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
        reportConfig.setSizePageToContent(true);
        reportConfig.setForceLineBreakPolicy(false);

        SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
        exportConfig.setMetadataAuthor(author);
        exportConfig.setEncrypted(true);
        exportConfig.setAllowedPermissionsHint("PRINTING");

        exporter.setConfiguration(reportConfig);
        exporter.setConfiguration(exportConfig);
        try {
            exporter.exportReport();
        } catch (JRException ex) {
            log.fatal("Failed to export pdf report",ex);
        }
    }

    public void exportToXlsx(String sheetName, OutputStream outputStream) {
        JRXlsxExporter exporter = new JRXlsxExporter();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

        SimpleXlsxReportConfiguration reportConfig = new SimpleXlsxReportConfiguration();
        reportConfig.setSheetNames(new String[] { sheetName });

        exporter.setConfiguration(reportConfig);

        try {
            exporter.exportReport();
        } catch (JRException ex) {
            log.fatal("Failed to export xlsx report",ex);
        }
    }

    public void exportToCsv(OutputStream outputStream) {
        JRCsvExporter exporter = new JRCsvExporter();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleWriterExporterOutput(outputStream));

        try {
            exporter.exportReport();
        } catch (JRException ex) {
            log.fatal("Failed to export csv report",ex);
        }

    }

    public void exportToHtml(OutputStream outputStream) {
        HtmlExporter exporter = new HtmlExporter();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleHtmlExporterOutput(outputStream));

        try {
            exporter.exportReport();
        } catch (JRException ex) {
            log.fatal("Failed to export html report",ex);
        }
    }
}
