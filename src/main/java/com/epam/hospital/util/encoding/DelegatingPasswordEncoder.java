package com.epam.hospital.util.encoding;

import java.util.HashMap;
import java.util.Map;

public class DelegatingPasswordEncoder implements PasswordEncoder {
    private static final String PREFIX = "{";
    private static final String SUFFIX = "}";
    private final String idForEncode;
    private final PasswordEncoder passwordEncoderForEncode;
    private final Map<String, PasswordEncoder> idToPasswordEncoder;

    public DelegatingPasswordEncoder(String idForEncode, Map<String, PasswordEncoder> idToPasswordEncoder) {
        if (idForEncode == null) {
            throw new IllegalArgumentException("idForEncode cannot be null");
        } else if (!idToPasswordEncoder.containsKey(idForEncode)) {
            throw new IllegalArgumentException("idForEncode " + idForEncode + "is not found in idToPasswordEncoder " + idToPasswordEncoder);
        } else {

            for (String id : idToPasswordEncoder.keySet()) {
                if (id != null) {
                    if (id.contains("{")) {
                        throw new IllegalArgumentException("id " + id + " cannot contain " + "{");
                    }

                    if (id.contains("}")) {
                        throw new IllegalArgumentException("id " + id + " cannot contain " + "}");
                    }
                }
            }

            this.idForEncode = idForEncode;
            this.passwordEncoderForEncode = idToPasswordEncoder.get(idForEncode);
            this.idToPasswordEncoder = new HashMap<>(idToPasswordEncoder);
        }
    }

    public String encode(CharSequence rawPassword) {
        return PREFIX + this.idForEncode + SUFFIX + this.passwordEncoderForEncode.encode(rawPassword);
    }

    public boolean matches(CharSequence rawPassword, String prefixEncodedPassword) {
        if (rawPassword == null && prefixEncodedPassword == null) {
            return true;
        } else {
            String id = this.extractId(prefixEncodedPassword);
            PasswordEncoder delegate = this.idToPasswordEncoder.get(id);
            if (delegate == null) {
                throw new IllegalArgumentException("There is no PasswordEncoder mapped for the id \"" + id + "\"");
            } else {
                String encodedPassword = this.extractEncodedPassword(prefixEncodedPassword);
                return delegate.matches(rawPassword, encodedPassword);
            }
        }
    }

    private String extractId(String prefixEncodedPassword) {
        if (prefixEncodedPassword == null) {
            return null;
        } else {
            int start = prefixEncodedPassword.indexOf("{");
            if (start != 0) {
                return null;
            } else {
                int end = prefixEncodedPassword.indexOf("}", start);
                return end < 0 ? null : prefixEncodedPassword.substring(start + 1, end);
            }
        }
    }

    public boolean upgradeEncoding(String prefixEncodedPassword) {
        String id = this.extractId(prefixEncodedPassword);
        if (!this.idForEncode.equalsIgnoreCase(id)) {
            return true;
        } else {
            String encodedPassword = this.extractEncodedPassword(prefixEncodedPassword);
            return ((PasswordEncoder) this.idToPasswordEncoder.get(id)).upgradeEncoding(encodedPassword);
        }
    }

    private String extractEncodedPassword(String prefixEncodedPassword) {
        int start = prefixEncodedPassword.indexOf("}");
        return prefixEncodedPassword.substring(start + 1);
    }
}
