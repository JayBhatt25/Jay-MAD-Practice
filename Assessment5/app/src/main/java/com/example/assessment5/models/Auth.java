package com.example.assessment5.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Auth implements Serializable {
    String status,token,user_id,user_email,user_fname,user_lname;

    public Auth() {
    }

    public Auth(JSONObject obj) throws JSONException {
        this.status = obj.getString("status");
        this.token = obj.getString("token");
        this.user_id = obj.getString("user_id");
        this.user_email = obj.getString("user_email");
        this.user_fname = obj.getString("user_fname");
        this.user_lname = obj.getString("user_lname");

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_fname() {
        return user_fname;
    }

    public void setUser_fname(String user_fname) {
        this.user_fname = user_fname;
    }

    public String getUser_lname() {
        return user_lname;
    }

    public void setUser_lname(String user_lname) {
        this.user_lname = user_lname;
    }
}
