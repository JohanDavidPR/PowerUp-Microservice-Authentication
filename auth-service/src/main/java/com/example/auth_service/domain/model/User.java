package com.example.auth_service.domain.model;

import java.time.LocalDate;

public class User {
    int Id;
    String first_name;
    String last_name;
    LocalDate date_of_birth;
    String address;
    String phone_number;
    String email_address;
    Double base_salary;

    public User(String firstName, String lastName, LocalDate dateOfBirth, String address, String phoneNumber, String email, double baseSalary) {
        this.first_name = firstName.trim();
        this.last_name = lastName.trim();
        this.date_of_birth = dateOfBirth;
        this.address = address;
        this.phone_number = phoneNumber;
        this.email_address = email.trim().toLowerCase();
        this.base_salary = baseSalary;
    }

    public int getId() { return Id; }
    public String getFirstName() { return first_name; }
    public String getLastName() { return last_name; }
    public LocalDate getDateOfBirth() { return date_of_birth; }
    public String getAddress() { return address; }
    public String getPhoneNumber() { return phone_number; }
    public String getEmail() { return email_address; }
    public double getBaseSalary() { return base_salary; }
}
