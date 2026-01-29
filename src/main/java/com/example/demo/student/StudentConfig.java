package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository){
        return args -> {
            Student hamza = new Student(
                    "Hamza",
                    "hamza@email.com",
                    LocalDate.of(2003, Month.OCTOBER,21)
            );

            Student second = new Student(
                    "second",
                    "second@email.com",
                    LocalDate.of(2001, Month.OCTOBER,22)
            );

            repository.saveAll(
                    List.of(hamza,second)
            );
        };
    }
}
