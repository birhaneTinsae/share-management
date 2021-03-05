package com.enat.sharemanagement.shareholder;

import com.enat.sharemanagement.guardian.GuardianDTO;
import com.enat.sharemanagement.share.ShareDTO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ShareholderDTO extends Person implements Serializable {
    private long id;
    private BigDecimal paidSubscription;
    private int noOfShares;
    private MaritalStatus maritalStatus;
    private Status status;
    private List<GuardianDTO> guardian;
    private List<ShareDTO> shares;
}
