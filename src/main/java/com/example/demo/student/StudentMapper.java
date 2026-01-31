package com.example.demo.student;

import com.example.demo.student.Student;
import com.example.demo.student.StudentResponseDTO;

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
