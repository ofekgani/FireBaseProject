package com.example.firebaseproject;

public class Grade {
    /**
     * @auther Ofek gani
     * @Version 1.0
     * @since 14/7/2020
     */
    private String studentID;
    private String grade;
    private String subject;
    private String quarter;

    public  Grade()
    {

    }

    public String getGrade() {
        return grade;
    }

    public String getSubject() {
        return subject;
    }

    public String getQuarter() {
        return quarter;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
}
