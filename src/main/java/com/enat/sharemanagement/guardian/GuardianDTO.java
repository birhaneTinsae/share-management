package com.enat.sharemanagement.guardian;

import com.enat.sharemanagement.shareholder.Person;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper=true)
public class GuardianDTO extends Person implements Serializable {
    private long id;
    private String worksAt;


}
