package com.enat.sharemanagement.batch;

import com.enat.sharemanagement.shareholder.Shareholder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceInput {
    private Shareholder shareholder;

}

