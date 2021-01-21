package com.enat.sharemanagement.attendance;

import com.enat.sharemanagement.shareholder.ShareholderDTO;
import com.enat.sharemanagement.utils.Auditable;
import com.enat.sharemanagement.vote.CandidateDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AttendanceDTO extends Auditable implements Serializable {
    private long id;
    private ShareholderDTO shareholder;
    private boolean attend;
    private boolean voted;
    private String budgetYear;
    private List<CandidateDTO> candidates;

}
