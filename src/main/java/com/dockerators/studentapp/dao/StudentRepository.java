package com.dockerators.studentapp.dao;

import com.dockerators.studentapp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository <Student, Integer> {
    Optional<Student> findByEmail(String email);
}
