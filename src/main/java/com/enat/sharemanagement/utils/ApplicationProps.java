package com.enat.sharemanagement.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "application")
@Data
public class ApplicationProps {

    private List<String> allowedOrigin;

    // getter and setter

}
