package com.enat.sharemanagement.vote;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;
@Data
public class AttendanceMetricsDTO {
    private long shareholder;
    private BigDecimal share;
    private long totalShareholder;
    private BigDecimal totalShares;
}
