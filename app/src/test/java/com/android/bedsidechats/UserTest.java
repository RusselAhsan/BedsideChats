package com.android.bedsidechats;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class UserTest {
    @Test
    public void getEmail_ReturnsEmail(){
        User user = new User("email", "username", "password");
        assertThat(user.getEmail()).isEqualTo("email");
    }

    @Test
    public void getUsername_ReturnsUsername(){
        User user = new User("email", "username", "password");
        assertThat(user.getUsername()).isEqualTo("username");
    }

    @Test
    public void getPassword_ReturnsPassword(){
        User user = new User("email", "username", "password");
        assertThat(user.getPassword()).isEqualTo("password");
    }

}
