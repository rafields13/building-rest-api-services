package com.crud.payroll;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CUSTOMER_ORDER")
class Order {
    private @Id
    @GeneratedValue Long id;
    private String description;
    private Status status;

    public Order() {
    }

    Order(String description, Status status) {
        this.description = description;
        this.status = status;
    }

    public Long getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (!(object instanceof Order))
            return false;

        Order order = (Order) object;

        return Objects.equals(this.id, order.id) && Objects.equals(this.description, order.description) && Objects.equals(this.status, order.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.description, this.status);
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + this.id + ", description='" + this.description + '\'' + ", status=" + this.status + '}';
    }
}
