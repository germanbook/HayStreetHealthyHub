package com.example.haystreethealthyhub;

public class Appointment {

    private int ID;
    private int patientID;
    private int doctorID;
    private int status;
    private String note;
    private String date;
    private String time;
    private String visitReason;

    public Appointment(){}

    public Appointment(int id, int patientID, int doctorID, int status, String note,
                       String date, String time, String visitReason)
    {
        this.ID = id;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.status = status;
        this.note = note;
        this.date = date;
        this.time = time;
        this.visitReason = visitReason;
    }

    public Appointment(int patientID, int doctorID, int status,
                       String date, String time, String visitReason)
    {
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.status = status;
        this.date = date;
        this.time = time;
        this.visitReason = visitReason;
    }

    public int getID() {
        return ID;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVisitReason() {
        return visitReason;
    }

    public void setVisitReason(String visitReason) {
        this.visitReason = visitReason;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
