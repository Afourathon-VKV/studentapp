package com.dockerators.studentapp.service;

import com.dockerators.studentapp.dao.StudentRepository;
import com.dockerators.studentapp.entity.Student;
import com.dockerators.studentapp.exception.NullFieldsException;
import com.dockerators.studentapp.exception.StudentAlreadyExistsException;
import com.dockerators.studentapp.exception.StudentNotFoundException;
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
        }else{
            // Will throw error if a student with that ID is not found
            throw new StudentNotFoundException();
        }
    }

    @Override
    public Student updateStudent(Student student) throws RuntimeException{
        try{
            Optional <Student> result = this.studentRepository.findByRollNo(student.getRollNo());
            if(result.isEmpty()){
                // Will throw error if a student with that ID is not found
                throw new StudentNotFoundException();
            }
            student.setId(result.get().getId());
            return this.studentRepository.save(student);
        } catch (StudentNotFoundException e) {
            throw e;
        } catch (RuntimeException e){
            // Will throw error if any of the fields are empty.
            throw new NullFieldsException();
        }
    }

    @Override
    // Adds a student to the database
    public Student save(Student student) {
        try{
            Optional<Student> s = this.studentRepository.findByRollNo(student.getRollNo());
            if(s.isPresent()){
                throw new StudentAlreadyExistsException();
            }else{
                return (this.studentRepository.save(student));
            }
        }catch (StudentAlreadyExistsException e){
            // Will throw error if a student with that Roll Number already exists
            throw e;
        }catch (RuntimeException e){
            // Will throw error if any of the fields are NULL.
            throw new NullFieldsException();
        }

    }

    @Override
    // Deletes a student that corresponds to an id
    public Student deleteById(int id) {
        Optional<Student> b = this.studentRepository.findById(id);
        if (b.isEmpty()) {
            // Will throw error if a student with that ID does not exist
            throw new StudentNotFoundException();
        } else {
            this.studentRepository.deleteById(id);
            return b.get();
        }
    }
}
