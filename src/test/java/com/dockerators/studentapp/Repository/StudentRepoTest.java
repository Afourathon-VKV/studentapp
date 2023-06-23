package com.dockerators.studentapp.Repository;

import com.dockerators.studentapp.dao.StudentRepository;
import com.dockerators.studentapp.entity.Student;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase
public class StudentRepoTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveStudentTest(){
        Student student = new Student(1,"1","TestName","Test@email.com","123456789");
        studentRepository.save(student);
        Assertions.assertTrue(student.getId() > 0);
    }

    @Test
    @Order(2)
    @AutoConfigureTestDatabase
    public void getStudentTest(){
        Student search_employee = studentRepository.findById(1).get();
        Assertions.assertTrue((search_employee.getId()) == 1);
    }

    @Test
    @Order(3)
    @AutoConfigureTestDatabase
    public void getListOfStudentsTest(){
        List<Student> employees = studentRepository.findAll();
        Assertions.assertTrue((employees.size()) > 0);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    @AutoConfigureTestDatabase
    public void updateStudentTest(){
        Student employee = studentRepository.findById(1).get();
        employee.setEmail("ram@gmail.com");
        Assertions.assertTrue((employee.getEmail()).equals("ram@gmail.com"));
    }


    @Test
    @Order(5)
    @AutoConfigureTestDatabase
    public void deleteStudentTest(){
        Student employee = studentRepository.findById(1).get();
        studentRepository.delete(employee);
        Student employee1 = null;
        Optional<Student> optionalEmployee = studentRepository.findByEmail("ram@gmail.com");
        if(optionalEmployee.isPresent()){
            employee1 = optionalEmployee.get();
        }
        Assertions.assertTrue(employee1 == null);
    }

}

