package com.enat.sharemanagement.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @author btinsae
 * @version 1.0
 */
@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    @Value("${storage.location}")
    private String location ;

    /**
     * to get file storage directory
     *
     * @return file storage directory
     */
    public String getLocation() {
        return location;
    }

    /**
     * to set file storage directory
     *
     * @param location
     *
     */
    public void setLocation(String location) {
        this.location = location;
    }

}
