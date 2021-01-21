package com.enat.sharemanagement.guardian;

import com.enat.sharemanagement.shareholder.Person;
import com.enat.sharemanagement.shareholder.Shareholder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "guardians")
public class Guardian extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false,updatable = false)
    private long id;
    private String worksAt;
    @ManyToOne()
    @JoinColumn(referencedColumnName = "id",name = "shareholder_id")
    @JsonIgnore
    private Shareholder shareholder;


}
