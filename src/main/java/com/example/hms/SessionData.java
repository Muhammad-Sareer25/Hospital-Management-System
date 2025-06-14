package com.example.hms;
//this class is for logged admin   // with controller 2 connected


public class SessionData {
    private static String loggedInAdminUsername;

    public static void setLoggedInAdmin(String username) {
        loggedInAdminUsername = username;
    }

    public static String getLoggedInAdmin() {
        return loggedInAdminUsername;
    }
}
