package com.practica.demo.models;

public class SessionModelResponse {
    private String message;
    private String sessionToken;
    private String dataEncrypted;
    private Integer errorCode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getDataEncrypted() {
        return dataEncrypted;
    }

    public void setDataEncrypted(String dataEncrypted) {
        this.dataEncrypted = dataEncrypted;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
