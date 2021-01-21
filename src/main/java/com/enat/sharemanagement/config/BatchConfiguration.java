package com.enat.sharemanagement.config;

import com.enat.sharemanagement.batch.AttendanceInput;
import com.enat.sharemanagement.batch.AttendanceItemProcessor;
import com.enat.sharemanagement.batch.AttendanceOutput;

import com.enat.sharemanagement.batch.JobCompletionNotificationListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
//
//    public final JobBuilderFactory jobBuilderFactory;
//
//    public final StepBuilderFactory stepBuilderFactory;
////    @Value("${storage.active}")
////    private String path;
////    private ResourceLoader resourceLoader;
//
//    public BatchConfiguration(JobBuilderFactory jobBuilderFactory,
//                              StepBuilderFactory stepBuilderFactory,
//                              ResourceLoader resourceLoader) {
//        this.jobBuilderFactory = jobBuilderFactory;
//        this.stepBuilderFactory = stepBuilderFactory;
////        this.resourceLoader = resourceLoader;
//    }
//
//    @Bean
//    @JobScope
//    public FlatFileItemReader<AttendanceInput> reader() {
//        return new FlatFileItemReaderBuilder<AttendanceInput>()
//                .name("pinItemReader")
////                .linesToSkip(8)
//                .delimited()
//                .names("serialNo", "code", "denomination", "status", "expireAt", "createdAt", "purchaseOrderId","active")
//                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
//                    setTargetType(AttendanceInput.class);
//                }})
//                .build();
//    }
//
////    @Bean
////    @JobScope
////    public MultiResourceItemReader<AttendanceInput> multiResourceItemReader() {
////        MultiResourceItemReader<AttendanceInput> multiResourceItemReader = new MultiResourceItemReader<>();
////        // multiResourceItemReader.setResources(loadResources());
//////        FileSystemResource resource = new FileSystemResource("/home/birhane/pins-file/active/*.csv");
//////        Resource[] resources = {resource};
//////        System.out.println(path);
////        multiResourceItemReader.setResources(loadResources());
////        //   multiResourceItemReader.setResources(resources);
////        multiResourceItemReader.setDelegate(reader());
////        return multiResourceItemReader;
////    }
//
//    @Bean(name = "jdbcWriter")
//    public JdbcBatchItemWriter<AttendanceOutput> writer(DataSource dataSource) {
//        return new JdbcBatchItemWriterBuilder<AttendanceOutput>()
//                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
//                .sql("INSERT INTO pins (serial_no, code,denomination,status,expire_at,created_at,updated_at,created_by,purchase_order_id,active,status_change_count) VALUES (:serialNo, :code,:denomination,:status,:expireAt,:createdAt,:updatedAt,:createdBy,:purchaseOrderId,:active,0)")
//                .dataSource(dataSource)
//                .build();
//    }
//
//    @Bean
//    public AttendanceItemProcessor processor() {
//        return new AttendanceItemProcessor();
//    }
//
//
//    @Bean
//    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
//        return jobBuilderFactory.get("importPinsJob")
//                .incrementer(new RunIdIncrementer())
//                .listener(listener)
//                .flow(step1)
//                .end()
//                .build();
//    }
//
////    @Bean
////    public Step step1(@Qualifier("jdbcWriter") ItemWriter<AttendanceOutput> itemWriter) {
////        return stepBuilderFactory.get("step1")
////                .<AttendanceInput, AttendanceOutput>chunk(100)
////                .reader(multiResourceItemReader())
////                .processor(processor())
////                .writer(itemWriter)
////                .build();
////    }
//
////    public Resource[] loadResources() {
////        try {
////
////            return ResourcePatternUtils.getResourcePatternResolver(resourceLoader)
////                    .getResources(storageProperties.getPinFiles());//getResources("classpath:/input/*.csv");
////        } catch (IOException ex) {
////            //  Logger.getLogger(BatchConfiguration.class.getName()).log(Level.SEVERE, null, ex);
////        }
////        return null;
////    }
}
