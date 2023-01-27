package com.epam.hospital.pagination;

public class Pageable {
    private final String offset;
    private final String limit;
    private Sort sort;

    public Pageable(String offset, String limit, Sort sort) {
        this.offset = offset;
        this.limit = limit;
        this.sort = sort;
    }

    public Pageable(String offset, String limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public Pageable() {
        this.offset = "0";
        this.limit = "0";
    }

    public String getOffset() {
        return offset;
    }

    public String getLimit() {
        return limit;
    }

    public Sort getSort() {
        return sort;
    }

    public String query() {
        if (sort != null){
            if (limit.equals("0")){
                return sort.query();
            } else {
                return sort.query() + " LIMIT " + offset + ", " + limit;
            }
        } else {
            if (limit.equals("0")){
                return "";
            } else {
                return " LIMIT " + offset + ", " + limit;
            }
        }
    }
}
