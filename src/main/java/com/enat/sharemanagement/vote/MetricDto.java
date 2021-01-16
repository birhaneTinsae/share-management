/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enat.sharemanagement.vote;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;

/**
 * @author btinsae
 */

@Data
@AllArgsConstructor
public class MetricDto {
    private long shareholder;
    private BigDecimal share;

}
