package com.crud.payroll;

class OrderNotFoundException extends RuntimeException {
    OrderNotFoundException(Long id) {
        super("Could not found order " + id);
    }
}
