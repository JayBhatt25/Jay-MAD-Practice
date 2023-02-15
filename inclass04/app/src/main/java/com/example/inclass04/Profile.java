package com.example.inclass04;

import java.io.Serializable;

public class Profile implements Serializable {
    String name;
    String email;
    int id;
    String dept;

    public Profile(String name, String email, int id, String dept) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.dept = dept;
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                ", dept='" + dept + '\'' +
                '}';
    }
}
