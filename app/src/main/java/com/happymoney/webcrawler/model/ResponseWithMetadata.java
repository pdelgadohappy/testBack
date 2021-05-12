package com.happymoney.webcrawler.model;

public class ResponseWithMetadata {
    private String createdAt;
    private String status;
    private String result;

    public ResponseWithMetadata(String status, String result) {
        this.status = status;
        this.result = result;
        this.createdAt = java.time.Clock.systemUTC().instant().toString();
    }

    public ResponseWithMetadata() {
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
