package com.enat.sharemanagement.batch;

import com.enat.sharemanagement.shareholder.Shareholder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceOutput {
    private Shareholder shareholder;
    private boolean attend;
    private boolean voted;

}

