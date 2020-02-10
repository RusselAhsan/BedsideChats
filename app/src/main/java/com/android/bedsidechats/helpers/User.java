package com.android.bedsidechats.helpers;

public class User {
    private String mEmail;
    private String mUsername;
    private String mPassword;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email, String name, String password) {
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
