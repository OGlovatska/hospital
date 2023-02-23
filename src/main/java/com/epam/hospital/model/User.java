package com.epam.hospital.model;

import com.epam.hospital.model.enums.Role;
import com.epam.hospital.to.UserTo;

import java.time.LocalDate;
import java.util.Objects;

public class User extends Entity {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;

    public User() {
    }

    protected User(Builder<?> builder) {
        super(builder.id);
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.password = builder.password;
        this.role = builder.role;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

        User user = (User) o;

        if (!Objects.equals(firstName, user.firstName)) return false;
        if (!Objects.equals(lastName, user.lastName)) return false;
        if (!Objects.equals(email, user.email)) return false;
        if (!Objects.equals(password, user.password)) return false;
        return role == user.role;
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + super.getId() + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }

    public static class Builder<T extends Builder<T>> {
        private int id;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
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

        public T password(String password) {
            this.password = password;
            return (T) this;
        }

        public T role(Role role) {
            this.role = role;
            return (T) this;
        }

        public User build() {
            return new User(this);
        }
    }
}
