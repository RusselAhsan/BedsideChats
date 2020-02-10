package com.android.bedsidechats.helpers;

public class Account {
    private String mEmail;
    private String mUsername;
    private String mPassword;


    public Account() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Account(String email, String name, String password) {
        mEmail = email;
        mUsername = name;
        mPassword = password;
    }

    public String getEmail() { return mEmail; }

    public String getUsername() {
        return mUsername;
    }

    public String getPassword() {
        return mPassword;
    }


}
