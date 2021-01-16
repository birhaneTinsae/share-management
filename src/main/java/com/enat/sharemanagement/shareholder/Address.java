/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enat.sharemanagement.shareholder;

import lombok.Data;
import org.springframework.data.geo.Point;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 *
 * @author birhane
 */
@Embeddable
@Data
public class Address implements Serializable {
    private String state;
    private String city;
    private String subCity;
    private String woredaOrKebele;
//    private String street;
    private String houseNo;
    private Point location;
}

