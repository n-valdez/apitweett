package com.example.twitterapi.api.bean;

public class ResponseConnectionStatus {

    public ResponseConnectionStatus() {
    }

    private Boolean backendConnection;
    private Boolean dataBaseConnection;
    private String backendVersion;

    public String getBackendVersion() {
        return backendVersion;
    }

    public void setBackendVersion(String backendVersion) {
        this.backendVersion = backendVersion;
    }

    public Boolean getBackendConnection() {
        return backendConnection;
    }

    public void setBackendConnection(Boolean status) {
        this.backendConnection = status;
    }

    public Boolean getDataBaseConnection() {
        return dataBaseConnection;
    }

    public void setDataBaseConnection(Boolean dataBaseConnection) {
        this.dataBaseConnection = dataBaseConnection;
    }
}
