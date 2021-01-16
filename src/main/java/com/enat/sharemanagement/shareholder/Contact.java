/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enat.sharemanagement.shareholder;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.validation.constraints.Email;
import java.io.Serializable;

/**
 *
 * @author birhane
 */
@Embeddable
@Data
public class Contact implements Serializable {

    @Email(message = "Email should be valid")
    private String email;
    private String postBoxNo;
    private String phoneNo;
    private String phoneNoTwo;
    private String phoneNoThree;

}
