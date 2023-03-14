package com.epam.hospital.util.encoding;

import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BCryptPasswordEncoder implements PasswordEncoder {
    private final Pattern BCRYPT_PATTERN;
    private final int strength;
    private final String version;
    private final SecureRandom random;

    public BCryptPasswordEncoder() {
        BCRYPT_PATTERN = Pattern.compile("\\A\\$2(a|y|b)?\\$(\\d\\d)\\$[./0-9A-Za-z]{53}");
        strength = 10;
        version = "$2a";
        random = new SecureRandom();
    }

    public String encode(CharSequence rawPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        } else {
            String salt = this.getSalt();
            return BCrypt.hashPassword(rawPassword.toString(), salt);
        }
    }

    private String getSalt() {
        return BCrypt.generateSalt(this.version, this.strength, this.random);
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        } else if (encodedPassword != null && encodedPassword.length() != 0) {
            if (!this.BCRYPT_PATTERN.matcher(encodedPassword).matches()) {
                return false;
            } else {
                return BCrypt.checkPassword(rawPassword.toString(), encodedPassword);
            }
        } else {
            return false;
        }
    }

    public boolean upgradeEncoding(String encodedPassword) {
        if (encodedPassword != null && encodedPassword.length() != 0) {
            Matcher matcher = this.BCRYPT_PATTERN.matcher(encodedPassword);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("Encoded password does not look like BCrypt: " + encodedPassword);
            } else {
                int strength = Integer.parseInt(matcher.group(2));
                return strength < this.strength;
            }
        } else {
            return false;
        }
    }
}
