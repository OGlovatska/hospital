package com.epam.hospital.util.encoding;

public class NoOpPasswordEncoder implements PasswordEncoder {
    public NoOpPasswordEncoder() {
    }

    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.toString().equals(encodedPassword);
    }
}
