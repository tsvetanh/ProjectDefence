package com.pdefence.entity;


import com.pdefence.entity.enums.Status;

import java.util.Date;

public class Request {
    private String id;
    private String type;
    private Date date;
    private String createdBy;
    private int hour;
    private Status status;

    public Request() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Request{" +
                "type='" + type + '\'' +
                ", date=" + date +
                ", createdBy='" + createdBy + '\'' +
                ", hour=" + hour +
                ", status=" + status +
                '}';
    }

}
