package com.mak.pcr.dbentities;

public class Faculty extends Person{

    public String email;

    public Faculty(){}

    public Faculty(String fname, String lname, String gender, String email) {
        super(fname, lname, gender);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

}

