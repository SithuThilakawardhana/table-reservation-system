package com.example.model;

import java.sql.Date;
import java.sql.Time;

public class Reservation {
    private int id;
    private int resId;
    private String title;
    private String firstName;
    private String lastName;
    private String phone;
    private Date date;
    private Time time;
    private int pax;
    private String remarks;
    private boolean isActive;

    public Reservation() {
    }

    public Reservation(int id, int resId, String title, String firstName, String lastName, String phone, Date date, Time time, int pax, String remarks, boolean isActive) {
        this.id = id;
        this.resId = resId;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.date = date;
        this.time = time;
        this.pax = pax;
        this.remarks = remarks;
        this.isActive = isActive;
    }

    public Reservation(int resId, String title, String firstName, String lastName, String phone, Date date, Time time, int pax, String remarks, boolean isActive) {
        this.resId = resId;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.date = date;
        this.time = time;
        this.pax = pax;
        this.remarks = remarks;
        this.isActive = isActive;
    }

    public Reservation(int id, String title, String firstName, String lastName, String phone, boolean isActive) {
        this.id = id;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.isActive = isActive;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public int getPax() {
        return pax;
    }

    public void setPax(int pax) {
        this.pax = pax;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    
}


