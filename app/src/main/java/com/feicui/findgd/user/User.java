package com.feicui.findgd.user;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LINS on 2017/1/10.
 * Please Try Hard
 */
public class User {
    /**
     * UserName : qjd
     * Password : 654321
     */

    @SerializedName("UserName")
    private String name;
    private String Password;

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        Password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }
}
