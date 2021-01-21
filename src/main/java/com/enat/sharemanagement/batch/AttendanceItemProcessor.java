package com.enat.sharemanagement.batch;

import org.springframework.batch.item.ItemProcessor;

public class AttendanceItemProcessor implements ItemProcessor<AttendanceInput, AttendanceOutput> {
    @Override
    public AttendanceOutput process(AttendanceInput attendanceInput) throws Exception {
        var p = new AttendanceOutput();
        p.setShareholder(attendanceInput.getShareholder());
        return p;
    }
}
