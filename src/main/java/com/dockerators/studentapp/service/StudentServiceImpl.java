package com.dockerators.studentapp.service;

import com.dockerators.studentapp.dao.StudentRepository;
import com.dockerators.studentapp.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    // Private repository to access the student database and perform SQL Queries
    private StudentRepository studentRepository;

    @Autowired
    // Constructor
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    // Will return a list of all students in the database
    public List<Student> findAll() {
        return (this.studentRepository.findAll());
    }

    @Override
    // Will return a student from the database that corresponds to an id
    public Student findById(int id) {
        // May be null.
        Optional<Student> result = this.studentRepository.findById(id);

        Student student;
        if(result.isPresent()) {
            student = result.get();
            return(student);
        }
        else{
            throw new RuntimeException("Did not find student id - " + id);
        }
    }

    @Override
    // Adds a student to the database
    public Student save(Student student) {
        return (this.studentRepository.save(student));
    }

    @Override
    // Deletes a student that corresponds to an id
    public void deleteById(int id) {
        this.studentRepository.deleteById(id);
    }
}
