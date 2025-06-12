package com.example.hms;
//this class is for logged admin


public class SessionData {
    private static String loggedInAdminUsername;

    public static void setLoggedInAdmin(String username) {
        loggedInAdminUsername = username;
    }

    public static String getLoggedInAdmin() {
        return loggedInAdminUsername;
    }
}
