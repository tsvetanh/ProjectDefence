package com.pdefence.entity;


import java.util.Date;

public class Request {
    private String type;
    private Date date;
    private String createdBy;
    private int hour;

    public Request() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    @Override
    public String toString() {
        return "Request{" +
                "type='" + type + '\'' +
                ", date=" + date +
                ", createdBy='" + createdBy + '\'' +
                ", hour=" + hour +
                '}';
    }
}
