package com.dockerators.studentapp.service;

import com.dockerators.studentapp.dao.StudentRepository;
import com.dockerators.studentapp.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> findAll() {
        return (this.studentRepository.findAll());
    }

    @Override
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
    public Student save(Student student) {
        return (this.studentRepository.save(student));
    }

    @Override
    public void deleteById(int id) {
        this.studentRepository.deleteById(id);
    }
}
