package com.enat.sharemanagement.config;

import com.enat.sharemanagement.report.SimpleReportExporter;
import com.enat.sharemanagement.report.SimpleReportFiller;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasperReportConfig {

    @Bean
    public SimpleReportFiller reportFiller() {
        return new SimpleReportFiller();
    }

    @Bean
    public SimpleReportExporter reportExporter() {
        return new SimpleReportExporter();
    }
}
