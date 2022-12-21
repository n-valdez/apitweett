package com.example.twitterapi.api.bean.twitter;

public class ErrorResponseUser {
    private String value;
    private String detail;
    private String title;
    private String resource_type;
    private String parameter;
    private String resource_id;
    private String type;

    public ErrorResponseUser() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResource_type() {
        return resource_type;
    }

    public void setResource_type(String resource_type) {
        this.resource_type = resource_type;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getResource_id() {
        return resource_id;
    }

    public void setResource_id(String resource_id) {
        this.resource_id = resource_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ErrorResponseUser{" +
                "value='" + value + '\'' +
                ", detail='" + detail + '\'' +
                ", title='" + title + '\'' +
                ", resource_type='" + resource_type + '\'' +
                ", parameter='" + parameter + '\'' +
                ", resource_id='" + resource_id + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
