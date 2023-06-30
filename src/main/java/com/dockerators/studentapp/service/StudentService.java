package com.dockerators.studentapp.service;

import com.dockerators.studentapp.entity.Student;

import java.util.List;

// An interface for the implementation of the student service
public interface StudentService {
    public List<Student> findAll();
    public Student findById(int id);
    public Student save(Student student);
    public void  deleteById(int id);
}