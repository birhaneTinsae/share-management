package com.enat.sharemanagement.storage;

/**
 * Custom storage exception
 *
 * @author btinsae
 * @version 1.0
 */
public class StorageException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor with error message
     *
     * @param message
     */
    public StorageException(String message) {
        super(message);
    }

    /**
     * Constructor with error message and throwable
     *
     * @param message
     * @param cause
     */
    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
