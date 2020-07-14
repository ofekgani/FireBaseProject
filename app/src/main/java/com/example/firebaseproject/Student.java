package com.example.firebaseproject;

public class Student {

    /**
     * @auther Ofek gani
     * @Version 1.0
     * @since 14/7/2020
     */
    private String name;
    private String address;
    private String phone;
    private String homePhone;
    private String fatherName;
    private String fatherPhone;
    private String motherName;
    private String motherPhone;
    private String studentID;


    public Student(String name, String address, String phone, String homePhone, String fatherName, String fatherPhone, String motherName, String motherPhone, String studentID) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.homePhone = homePhone;
        this.fatherName = fatherName;
        this.fatherPhone = fatherPhone;
        this.motherName = motherName;
        this.motherPhone = motherPhone;
        this.studentID = studentID;
    }

    public Student(){

    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getFatherPhone() {
        return fatherPhone;
    }

    public String getMotherName() {
        return motherName;
    }

    public String getMotherPhone() {
        return motherPhone;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public void setFatherPhone(String fatherPhone) {
        this.fatherPhone = fatherPhone;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public void setMotherPhone(String motherPhone) {
        this.motherPhone = motherPhone;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
}


