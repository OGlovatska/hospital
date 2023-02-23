package com.epam.hospital.to;

import com.epam.hospital.model.enums.Role;

import java.util.Objects;

public class UserTo {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;

    public UserTo() {
    }

    public UserTo(int id, String firstName, String lastName, String email, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }

    protected UserTo(Builder<?> builder){
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.role = builder.role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserTo userTo = (UserTo) o;

        if (id != userTo.id) return false;
        if (!Objects.equals(firstName, userTo.firstName)) return false;
        if (!Objects.equals(lastName, userTo.lastName)) return false;
        if (!Objects.equals(email, userTo.email)) return false;
        return role == userTo.role;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserTo" +
                " id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'';
    }

    public static class Builder<T extends Builder<T>> {
        private int id;
        private String firstName;
        private String lastName;
        private String email;
        private Role role;

        public T id(int id) {
            this.id = id;
            return (T) this;
        }

        public T firstName(String firstName) {
            this.firstName = firstName;
            return (T) this;
        }

        public T lastName(String lastName) {
            this.lastName = lastName;
            return (T) this;
        }

        public T email(String email) {
            this.email = email;
            return (T) this;
        }

        public T role(Role role) {
            this.role = role;
            return (T) this;
        }

        public UserTo build(){
            return new UserTo(this);
        }
    }
}
