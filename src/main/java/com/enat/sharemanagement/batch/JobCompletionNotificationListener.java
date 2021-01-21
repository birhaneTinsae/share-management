package com.enat.sharemanagement.batch;


import com.enat.sharemanagement.storage.StorageProperties;
import com.enat.sharemanagement.storage.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private final StorageService storageService;
    private final StorageProperties storageProperties;


    @Override
    public void beforeJob(JobExecution jobExecution) {
        super.beforeJob(jobExecution);
    }

    @SneakyThrows
    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");
            // storageService.moveFile(storageProperties.getActive(), storageProperties.getArchive());
//            FileSystemUtils.copyRecursively(Path.of(storageProperties.getActive()),Path.of(storageProperties.getArchive()));
//            Files.walk(Path.of(storageProperties.getActive()))
//                    .filter(Files::isRegularFile)
//                    .map(Path::toFile)
//                    .forEach(File::delete);
        }
    }
}