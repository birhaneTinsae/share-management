package com.enat.sharemanagement.batch;

import com.enat.sharemanagement.attendance.AttendanceRepository;
import com.enat.sharemanagement.shareholder.Shareholder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
@RequiredArgsConstructor
public class AttendanceItemWriter implements ItemWriter<Shareholder> {
    private final AttendanceRepository repository;


    @Override
    public void write(List<? extends Shareholder> list) throws Exception {
        log.info(getClass().getName(), "writing to the database");
        //  repository.saveAll(list);
        System.out.println(list.size());
        log.info(getClass().getName(), "writing to the database completed");

    }
}
