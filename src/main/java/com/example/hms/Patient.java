
// with controller 3 connected


//Make a Java class Patient.java that matches your table structure:




package com.example.hms;

public class Patient {
    private String id;
    private String fname;
    private String lname;
    private Long contact;
    private String gender;
    private int age;
    private String address;

    public Patient(String id, String fname, String lname, Long contact, String gender, int age, String address) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.contact = contact;
        this.gender = gender;
        this.age = age;
        this.address = address;
    }

    // Getters and setters for each field

    public String getId() { return id; }
    public String getFname() { return fname; }
    public String getLname() { return lname; }
    public Long getContact() { return contact; }
    public String getGender() { return gender; }
    public int getAge() { return age; }
    public String getAddress() { return address; }
}
