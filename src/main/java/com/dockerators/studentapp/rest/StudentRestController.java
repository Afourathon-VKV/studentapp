package com.dockerators.studentapp.rest;

import com.dockerators.studentapp.entity.Student;
import com.dockerators.studentapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {
    // Private Student Service to obtain all functionalities of the service layer
    private StudentService studentService;

    @Autowired
    // Constructor
    public StudentRestController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping("/students")
    // Route to get all students
    public List<Student> findAll() {
        return (this.studentService.findAll());
    }

    @GetMapping("/students/{student_id}")
    // Route to get the student that corresponds to a student id
    // The id is taken as a path variable
    public Student findById(@PathVariable int student_id) {
        return (this.studentService.findById(student_id));
    }


    @PostMapping("/students")
    // Route to add a new student
    // The student object is accepted in the request body
    public Student addStudent(@RequestBody Student student) {
        student.setId(0);      // To force an add, not an update.
        return (this.studentService.save(student));
    }

    @PutMapping("/students")
    // Route to update a student
    // The student object is accepted in the request body
    public Student updateStudent(@RequestBody Student student) {
        return (this.studentService.updateStudent(student));
    }

    @DeleteMapping("/students/{student_id}")
    // Route to delete a student that corresponds to an id
    // The id of the student to be deleted is taken as a path variable
    public Student deleteStudent(@PathVariable int student_id) {
        return this.studentService.deleteById(student_id);
    }
}
