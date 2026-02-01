package com.example.demo.student.dto;

public class StudentResponseDTO {

    private Long id;
    private String name;
    private String email;
    private Integer age;

    public StudentResponseDTO(Long id, String name, String email, Integer age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    // getters only (no setters needed for responses)

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Integer getAge() {
        return age;
    }
}
