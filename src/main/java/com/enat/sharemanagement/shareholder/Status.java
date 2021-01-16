package com.enat.sharemanagement.shareholder;

public enum Status {
    ACTIVE('A'), RESIGNED('R'), TERMINATED('T'), DELETED('D'),PROBATION('P');
    private final char status;

    Status(char status) {
        this.status = status;
    }

    public char getStatus() {
        return status;
    }

}
