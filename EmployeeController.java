package com.crud.payroll;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.repository.support.Repositories;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
class EmployeeController {
    private final EmployeeRepository repository;
    private final EmployeeModelAssembler assembler; // Injecting assembler in the constructor

    EmployeeController(EmployeeRepository repository, EmployeeModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler; // Here and at the parameters of the function
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    // Before
//    @GetMapping("/employees")
//    List<Employee> all() {
//        return repository.findAll();
//    }
    // end::get-aggregate-root

    // After
    @GetMapping("/employees")
    CollectionModel<EntityModel<Employee>> all() {
        List<EntityModel<Employee>> employees = repository.findAll().stream()
                .map(assembler::toModel) // After assembler
                // Before assembler
//                        employee -> EntityModel.of(employee,
//                        linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),
//                        linkTo(methodOn(EmployeeController.class).all()).withRel("employees")))
                .collect(Collectors.toList());

        return CollectionModel.of(employees,
                linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
    }

    // Before
//    @PostMapping("/employees")
//    Employee newEmployee(@RequestBody Employee newEmployee) {
//        return repository.save(newEmployee);
//    }

    // After
    @PostMapping("/employees")
    ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee) {
        EntityModel<Employee> entityModel = assembler.toModel(repository.save(newEmployee));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    // Single item
    // Before
//    @GetMapping("/employees/{id}")
//    Employee one(@PathVariable Long id) {
//        return repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
//    }

    // After
    @GetMapping("/employees/{id}")
    EntityModel<Employee> one(@PathVariable Long id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        // Before assembler
//        return EntityModel.of(employee,
//                linkTo(methodOn(EmployeeController.class).one(id)).withSelfRel(),
//                linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));

        // After assembler
        return assembler.toModel(employee);
    }

    // Before
//    @PutMapping("/employees/{id}")
//    Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
//        return repository.findById(id)
//                .map(employee -> {
//                    employee.setName(newEmployee.getName());
//                    employee.setRole(newEmployee.getRole());
//
//                    return repository.save(employee);
//                })
//                .orElseGet(() -> {
//                    newEmployee.setId(id);
//
//                    return repository.save(newEmployee);
//                });
//    }

    // After
    @PutMapping("/employees/{id}")
    ResponseEntity<?> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
        Employee updatedEmployee = repository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());

                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);

                    return repository.save(newEmployee);
                });

        EntityModel<Employee> entityModel = assembler.toModel(updatedEmployee);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    // Before
//    @DeleteMapping("/employees/{id}")
//    void deleteEmployee(@PathVariable Long id) {
//        repository.deleteById(id);
//    }

    // After
    @DeleteMapping("/employees/{id}")
    ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
