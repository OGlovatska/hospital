package com.epam.hospital.util.pagination;

public class Sort {
    private Direction direction = Direction.ASC;
    private Order order = Order.ID;

    public Sort() {
    }

    public Sort(String order, String direction) {
        if (checkNotNull(order)) {
            this.order = Order.valueOf(order.toUpperCase());
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
        return order.getOrder();
    }

    public String query() {
        return "ORDER BY " + order.getOrder() + " " + direction;
    }

    public enum Direction {
        ASC,
        DESC;
    }

    public enum Order {
        ID("id"),
        DATE_TIME("date_time"),
        TYPE("type"),
        START_DATE("start_date"),
        END_DATE("end_date"),
        STATUS("status"),
        FIRST_NAME("first_name"),
        LAST_NAME("last_name"),
        DATE_OF_BIRTH("date_of_birth"),
        PATIENTS("patients"),
        SPECIALISATION("specialisation");

        private final String order;

        Order(String order) {
            this.order = order;
        }

        public String getOrder() {
            return order;
        }
    }
}