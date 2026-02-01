package com.example.demo.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Page<StudentResponseDTO> getStudents(
            String name,
            Pageable pageable
    ) {
        Page<Student> students;

        if (name != null && !name.isBlank()) {
            students = studentRepository
                    .findByNameContainingIgnoreCase(name, pageable);
        } else {
            students = studentRepository.findAll(pageable);
        }

        return students.map(StudentMapper::toResponseDTO);
    }


    /* before filtering
    public Page<StudentResponseDTO> getStudents(Pageable pageable) {
        return studentRepository
                .findAll(pageable)
                .map(StudentMapper::toResponseDTO);
    }
    */

    /* before pagination
    public List<StudentResponseDTO> getStudents() {
        return studentRepository.findAll()
                .stream()
                .map(StudentMapper::toResponseDTO)// this is the shorter version of this instead : .map(student -> StudentMapper.toResponseDTO(student))

                .toList();
    }
    */
    public void addNewStudent(StudentRequestDTO dto) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(dto.getEmail());
        if(studentOptional.isPresent()){
            throw new IllegalStateException("email taken");
        }
        Student student = new Student(
                dto.getName(),
                dto.getEmail(),
                dto.getDob()
        );
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId){
        boolean exists = studentRepository.existsById(studentId);
        if(!exists){
            throw new IllegalStateException("student with id " + studentId + " does not exist");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional // so we don't use studentRepository.save(new Student(...)) + Ensures rollback on failure + Prevents partial updates
    public void updateStudent(Long studentId, StudentUpdateDTO dto) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("Student not found"));

        if(dto.getName() != null && !dto.getName().isEmpty())
            student.setName(dto.getName());

        if(dto.getEmail() != null && !dto.getEmail().isEmpty())
            student.setEmail(dto.getEmail());
    }


    /*
    public void updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(()-> new IllegalStateException(" student with id " + studentId + " does not exist"));

        if(name != null && name.length() > 0 && !Objects.equals(student.getName(),name)){
            student.setName(name);
        }

        if(email != null && email.length() > 0 && !Objects.equals(student.getEmail(),email)){
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if(studentOptional.isPresent()){
                throw new IllegalStateException("email taken");
            }
            student.setEmail(email);
        }
    } */

}
