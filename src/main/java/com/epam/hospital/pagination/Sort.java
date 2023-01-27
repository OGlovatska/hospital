package com.epam.hospital.pagination;

public class Sort {
    private Direction direction = Direction.ASC;
    private String order = "id";

    public Sort(){
    }

    public Sort(String order, String direction) {
        if (checkNotNull(order)) {
            this.order = order;
        }
        if (checkNotNull(direction)) {
            this.direction = Direction.valueOf(direction.toUpperCase());
        }
    }

    private boolean checkNotNull(String s) {
        return s != null && !s.isEmpty();
    }

    public String getDirection() {
        return direction.name();
    }

    public String getOrder() {
        return order;
    }

    public String query() {
        return "ORDER BY " + order + " " + direction;
    }

    private enum Direction {
        ASC,
        DESC;
    }
}
