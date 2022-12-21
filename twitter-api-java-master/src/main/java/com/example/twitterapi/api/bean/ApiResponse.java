package com.example.twitterapi.api.bean;

import java.util.List;

public class ApiResponse {

    private Boolean success;
    private List<ApiData> data;
    private Integer statusCode;

    public ApiResponse(Boolean success, List<ApiData> data, Integer statusCode) {
        this.success = success;
        this.data = data;
        this.statusCode = statusCode;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }


    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public List<ApiData> getData() {
        return data;
    }

    public void setData(List<ApiData> data) {
        this.data = data;
    }
}
