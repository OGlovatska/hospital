package com.epam.hospital.encoding;

import java.util.HashMap;
import java.util.Map;

public class PasswordEncoderFactories {
    private PasswordEncoderFactories() {
    }

    public static PasswordEncoder createDelegatingPasswordEncoder() {
        String encodingId = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(encodingId, new BCryptPasswordEncoder());
        encoders.put("noop", new NoOpPasswordEncoder());
        return new DelegatingPasswordEncoder(encodingId, encoders);
    }
}
