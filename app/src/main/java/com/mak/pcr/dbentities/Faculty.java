package com.mak.pcr.dbentities;

public class Faculty extends Person{

    public String email;
    public String contact;

    public Faculty(){}

    public Faculty(String fname, String lname, String gender, String email, String contact) {
        super(fname, lname, gender);
        this.email = email;
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName(){
        String _prefix = this.gender.matches("Male") ? "Sir" : "Miss";
        return _prefix + " " + this.fname + " " + this.lname;
    }

}

