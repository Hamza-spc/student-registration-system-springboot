package com.example.demo.student.mapper;

import com.example.demo.student.model.Student;
import com.example.demo.student.dto.StudentResponseDTO;

public class StudentMapper {

    private StudentMapper() {
        // prevents instantiation
    }

    public static StudentResponseDTO toResponseDTO(Student student) {
        return new StudentResponseDTO(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getAge()
        );
    }
}
