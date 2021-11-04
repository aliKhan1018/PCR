package com.mak.pcr.dbentities;

public class Student extends Person{

    String studentId, parentContact, parentEmail, batch_id;

    public Student(){}

    public Student(String studentId, String fname, String lname, String gender, String parentContact, String parentEmail, String batch_id) {
        super(fname, lname, gender);
        this.studentId = studentId;
        this.parentContact = parentContact;
        this.parentEmail = parentEmail;
        this.batch_id = batch_id;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getParentContact() {
        return parentContact;
    }

    public String getParentEmail() {
        return parentEmail;
    }

    public String getBatch_id() {
        return batch_id;
    }
}