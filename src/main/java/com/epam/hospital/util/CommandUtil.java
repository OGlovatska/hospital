package com.epam.hospital.util;

import java.util.Arrays;
import java.util.StringJoiner;

import static com.epam.hospital.command.constant.Command.COMMAND;
import static com.epam.hospital.command.constant.Command.CONTROLLER;

public class CommandUtil {

    public static String getPageToRedirect(String command, String... parameters) {
        String url = CONTROLLER + "?" + COMMAND + "=" + command;
        if (parameters != null && !Arrays.asList(parameters).isEmpty()) {
            StringJoiner joiner = new StringJoiner("&", "&", "");
            Arrays.stream(parameters).forEach(joiner::add);
            url += joiner;
        }
        return url;
    }

    private CommandUtil() {
    }
}
