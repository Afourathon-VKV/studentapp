package com.dockerators.studentapp.dao;

import com.dockerators.studentapp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository <Student, Integer> {
}
