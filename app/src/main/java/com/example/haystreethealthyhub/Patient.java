package com.example.haystreethealthyhub;

public class Patient extends User {

    private int gender;
    private String dob;
    private int height;
    private int weight;
    private int gp;

    public Patient(){};

    // This Constructor used by DBHelper.getAllPatientsLogin()
    public Patient(int id, String firstName, String email, String password)
    {
        super(id, firstName, email, password);
    };

    public Patient(String firstName, String lastName, String email, String password, int gender, String dob, int height, int weight, int gp)
    {
        super.firstName = firstName;
        super.lastName = lastName;
        super.email = email;
        super.password = password;
        this.gender = gender;
        this.dob = dob;
        this.height = height;
        this.weight = weight;
        this.gp = gp;

    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getGender() {
        return gender;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDob() {
        return dob;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public int getGp() {
        return gp;
    }

    public void setGp(int gp) {
        this.gp = gp;
    }
}
