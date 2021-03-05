package com.enat.sharemanagement.batch;

import com.enat.sharemanagement.shareholder.Shareholder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
@RequiredArgsConstructor
public class AttendanceItemWriter implements ItemWriter<Shareholder> {


    @Override
    public void write(List<? extends Shareholder>   list) {
        log.info(getClass().getName(), "writing to the database");
        log.info(getClass().getName(), "writing to the database completed");

    }
}
