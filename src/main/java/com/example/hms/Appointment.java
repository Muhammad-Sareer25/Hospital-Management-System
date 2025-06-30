package com.example.hms;

import javafx.beans.property.SimpleStringProperty;

public class Appointment {
    private int appId;
    private String appdate;
    private String patId;
    private String docId;

    public Appointment(int appId, String appName, String patId, String docId) {
        this.appId = appId;
        this.appdate = appName;
        this.patId = patId;
        this.docId = docId;
    }

    // Getters and setters here (for JavaFX TableView binding)
    public int getAppId() { return appId; }
    public String getAppName() { return appdate; }
    public String getDocId() { return patId; }
    public String getPatId() { return docId; }

}