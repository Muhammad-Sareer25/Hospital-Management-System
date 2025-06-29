package com.example.hms;


public class Doctor {
    private String docId;
    private String docName;
    private String specialization;
    private String patId;
    private String docContact;
    private String docAddress;

    public Doctor(String docId, String docName, String specialization, String patId, String docContact, String docAddress) {
        this.docId = docId;
        this.docName = docName;
        this.specialization = specialization;
        this.patId = patId;
        this.docContact = docContact;
        this.docAddress = docAddress;
    }

    // Getters
    public String getDocId() { return docId; }
    public String getDocName() { return docName; }
    public String getSpecialization() { return specialization; }
    public String getPatId() { return patId; }
    public String getDocContact() { return docContact; }
    public String getDocAddress() { return docAddress; }
}

