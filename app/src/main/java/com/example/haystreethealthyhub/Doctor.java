package com.example.haystreethealthyhub;

public class Doctor extends User{


    public Doctor(int id, String email, String password)
    {
        super(id, email, password);
    }

    public Doctor(String firstName, String lastName, String email, String password)
    {
        super.firstName = firstName;
        super.lastName = lastName;
        super.email = email;
        super.password = password;
    }

}
