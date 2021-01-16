package com.enat.sharemanagement.guardian;

import com.enat.sharemanagement.shareholder.Person;
import lombok.Data;

import java.io.Serializable;

@Data
public class GuardianDTO extends Person implements Serializable {
    private long id;
    private String worksAt;


}
