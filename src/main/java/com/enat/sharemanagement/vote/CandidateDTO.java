package com.enat.sharemanagement.vote;

import com.enat.sharemanagement.attendance.Attendance;
import com.enat.sharemanagement.shareholder.Shareholder;
import com.enat.sharemanagement.shareholder.ShareholderDTO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CandidateDTO implements Serializable {
    private long id;
//    private double totalVotes;
//    private String firstName;
//    private String middleName;
//    private String lastName;
    private BigDecimal totalVotes;
    private ShareholderDTO shareholder;
    private Attendance attendance;
}
