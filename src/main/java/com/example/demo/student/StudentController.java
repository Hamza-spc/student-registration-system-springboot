package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping
    public List<StudentResponseDTO> getStudents() {
        return studentService.getStudents();
    }

    @PostMapping
    public void registerNewStudent(@RequestBody StudentRequestDTO dto){ //Tells Spring to take the JSON in the request body and convert it into a Student object.
        studentService.addNewStudent(dto);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId){
        studentService.deleteStudent(studentId);

    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(
            @PathVariable Long studentId,
            @RequestBody StudentUpdateDTO dto
    ) {
        studentService.updateStudent(studentId, dto);
    }


    /*
    @PutMapping(path = "{studentId}")
    public void updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email){
        studentService.updateStudent(studentId,name,email);
    }
     */
}
/*
This project follows a layered design using Spring Boot and demonstrates basic REST API principles with dependency injection.

When a client sends an HTTP request (e.g., GET) to a URL like "api/v1/student", Spring first identifies which class handles HTTP requests by looking for @RestController. It then matches the request to the appropriate method using annotations like @GetMapping, @PostMapping, @PutMapping, or @DeleteMapping.

The controller often depends on a service class to handle business logic. Service classes are annotated with @Service, which tells Spring to create and manage a single instance of that class in the Spring Container at application startup. When the controller is created, Spring notices dependencies annotated with @Autowired and automatically injects the required service instance from the container.

This flow ensures separation of concerns: the controller handles HTTP requests, the service handles business logic, and Spring manages object creation and dependency wiring automatically.
*/
