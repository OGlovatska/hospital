package com.epam.hospital.command;

public class CommandResult {
    private String page;
    private final boolean isRedirect;

    public CommandResult(String page) {
        this.page = page;
        isRedirect = false;
    }

    public CommandResult(String page, boolean isRedirect) {
        this.page = page;
        this.isRedirect = isRedirect;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public boolean isRedirect() {
        return isRedirect;
    }
}
