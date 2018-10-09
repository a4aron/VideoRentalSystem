package model;

import java.io.Serializable;

public final class Staff extends Person implements Serializable{

    private String username;
    private String password;
    private int role;
    private double salary;

    public Staff(long id) {
        super(id);
    }

    public Staff(long id, String firstName, String lastName, String phone, String address, String username, String password, int role, double salary) {
        this(firstName, lastName, phone, address, username, password, role, salary);
        this.setId(id);
    }

    public Staff(String firstName, String lastName, String phone, String address, String username, String password, int role, double salary) {
        super(firstName, lastName, phone, address);
        this.username = username;
        this.password = password;
        this.role = role;
        this.salary = salary;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
