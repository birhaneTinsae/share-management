/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enat.sharemanagement.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author btinsae
 */
@RestController
@RequestMapping("/import-pins")
@Log4j2
@RequiredArgsConstructor
public class ImportPINResource {

//    final JobLauncher jobLauncher;
//
//    final Job job;
//
//
//    @GetMapping()
//    public BatchResult importPins() throws JobParametersInvalidException,
//            JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
//        JobParameters jobParameters = new JobParametersBuilder().addLong("uniqueness", System.nanoTime()).toJobParameters();
//
//        jobLauncher.run(job, jobParameters);
//
//        return new BatchResult("Done");
//    }
}
