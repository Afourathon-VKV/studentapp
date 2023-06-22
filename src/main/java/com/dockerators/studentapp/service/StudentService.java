package com.dockerators.studentapp.service;

import com.dockerators.studentapp.entity.Student;

import java.util.List;

public interface StudentService {
    public List<Student> findAll();
    public Student findById(int id);
    public Student save(Student employee);
    public void  deleteById(int id);
}
