package com.epam.hospital.util.pagination;

public class Pageable {
    private final int offset;
    private final int limit;
    private Sort sort;

    public Pageable(int offset, int limit, Sort sort) {
        this.offset = offset;
        this.limit = limit;
        this.sort = sort;
    }

    public Pageable(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public Pageable() {
        this.offset = 0;
        this.limit = 0;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public Sort getSort() {
        return sort;
    }

    public String query() {
        if (sort != null){
            return limit == 0 ? sort.query() : sort.query() + " LIMIT " + offset + ", " + limit;
        } else {
            return limit == 0 ? "" : " LIMIT " + offset + ", " + limit;
        }
    }
}