package com.crud.payroll;

class EmployeeNotFoundException extends RuntimeException {
    EmployeeNotFoundException(Long id) {
        super("Could not found employee " + id);
    }
}
