package com.dockerators.studentapp.service;

import com.dockerators.studentapp.dao.StudentRepository;
import com.dockerators.studentapp.entity.Student;
import com.dockerators.studentapp.exception.NullFieldsException;
import com.dockerators.studentapp.exception.StudentAlreadyExistsException;
import com.dockerators.studentapp.exception.StudentNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    // Private repository to access the student database and perform SQL Queries
    private StudentRepository studentRepository;
    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    // Constructor
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    // Will return a list of all students in the database
    public List<Student> findAll() {
        logger.info("Retrieving All Students");
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
            logger.info(String.format("Student with Id %s was retrieved.", id));
            return(student);
        }else{
            // Will throw error if a student with that ID is not found
            logger.error(String.format("Student with Id %s was not found.", id));
            throw new StudentNotFoundException();
        }
    }

    @Override
    public Student updateStudent(Student student) throws RuntimeException{
        try{
            Optional <Student> result = this.studentRepository.findByRollNo(student.getRollNo());
            if(result.isEmpty()){
                // Will throw error if a student with that roll number is not found
                throw new StudentNotFoundException();
            }
            student.setId(result.get().getId());
            this.studentRepository.save(student);
            logger.info(String.format("Student with Roll No %s was updated.", student.getRollNo()));
            return student;
        } catch (StudentNotFoundException e) {
            logger.error(String.format("Student with Roll No %s does not exist and hence can't be updated.", student.getRollNo()));
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
                this.studentRepository.save(student);
                logger.info(String.format("Student with Roll No %s was added.", student.getRollNo()));
                return student;
            }
        }catch (StudentAlreadyExistsException e){
            // Will throw error if a student with that Roll Number already exists
            logger.error(String.format("Student with Roll No %s already exists and hence can't be added.", student.getRollNo()));
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
            logger.error(String.format("Student with Id %s doesn't exist and hence can't be deleted.", id));
            throw new StudentNotFoundException();
        } else {
            this.studentRepository.deleteById(id);
            logger.info(String.format("Student with Id %s has been deleted.", id));
            return b.get();
        }
    }
}
