package com.practica.demo.models;

import java.time.LocalDateTime;

public class UserDTO {
    private String email;
    private String name;
    private String surename;
    private String lastname;
    private Integer type;
    private String phone;
    private Integer icn;
    private Integer id_hospital;
    private String shift_type;
    private Integer is_Active;
    private String date;
    private String usertoken;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurename() {
        return surename;
    }

    public void setSurename(String surename) {
        this.surename = surename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getIcn() {
        return icn;
    }

    public void setIcn(Integer icn) {
        this.icn = icn;
    }

    public Integer getId_hospital() {
        return id_hospital;
    }

    public void setId_hospital(Integer id_hospital) {
        this.id_hospital = id_hospital;
    }

    public String getShift_type() {
        return shift_type;
    }

    public void setShift_type(String shift_type) {
        this.shift_type = shift_type;
    }

    public Integer getIs_Active() {
        return is_Active;
    }

    public void setIs_Active(Integer is_Active) {
        this.is_Active = is_Active;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsertoken() {
        return usertoken;
    }

    public void setUsertoken(String usertoken) {
        this.usertoken = usertoken;
    }
}
