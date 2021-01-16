package com.enat.sharemanagement.vote;

import com.enat.sharemanagement.attendance.Attendance;
import lombok.Data;

import java.io.Serializable;

@Data
public class CandidateDTO implements Serializable {
    private long id;
    private double totalVotes;
    private String firstName;
    private String middleName;
    private String lastName;
    private Attendance attendance;
}
