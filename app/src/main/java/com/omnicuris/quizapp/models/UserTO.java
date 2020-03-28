package com.omnicuris.quizapp.models;

import java.io.Serializable;

public class UserTO implements Serializable {
    private String name = null;
    private String email = null;
    private String mobile = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
