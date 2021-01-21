package com.enat.sharemanagement.batch;

import com.enat.sharemanagement.shareholder.Shareholder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceOutput {
    private Shareholder shareholder;
    private boolean attend;
    private boolean voted;

}

