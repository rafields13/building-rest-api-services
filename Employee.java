package com.crud.payroll;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;

@Entity
class Employee {
    // Attributes
    private @Id
    @GeneratedValue Long id;

    // Before - change all the other code's parts
//    private String name;

    // After
    private String firstName;
    private String lastName;

    private String role;

    // Constructor
    public Employee() {
    } // Attention on this point, maybe isn't public!

    // Adicional constructor
    Employee(String firstName, String lastName, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    // Getters
    public Long getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getName() {
        return this.firstName + " " + this.lastName;
    }

    public String getRole() {
        return this.role;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setName(String name) {
        String[] parts = name.split(" ");
        this.firstName = parts[0];
        this.lastName = parts[1];
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Methods
    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (!(object instanceof Employee))
            return false;

        Employee employee = (Employee) object;

        return Objects.equals(this.id, employee.id) && Objects.equals(this.firstName, employee.firstName) && Objects.equals(this.lastName, employee.lastName) && Objects.equals(this.role, employee.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.firstName, this.lastName, this.role);
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + this.id + ", firstName='" + this.firstName + '\'' + ", lastName='" + this.lastName + '\'' + ", role='" + this.role + '\'' + '}';
    }
}
