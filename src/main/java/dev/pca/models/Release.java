package dev.pca.models;

import java.util.Date;

public class Release {

    private String setCode;
    private String name;
    private Date Date;

    public Release(String setCode, String name, Date Date) {
        this.setCode = setCode;
        this.name = name;
        this.Date = Date;
    }

    public String getSetCode() {
        return setCode;
    }

    public void setSetCode(String setCode) {
        this.setCode = setCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date Date) {
        this.Date = Date;
    }
}
