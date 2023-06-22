package com.dockerators.studentapp.rest;

import com.dockerators.studentapp.entity.Student;
import com.dockerators.studentapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {
    private StudentService studentService;

    @Autowired
    public StudentRestController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public List<Student> findAll() {
        return (this.studentService.findAll());
    }

    @GetMapping("/students/{student_id}")
    public Student findById(@PathVariable int student_id) {
        Student student = this.studentService.findById(student_id);
        if (student == null) {
            throw new RuntimeException("Student id not found - " + student_id);
        }
        return (student);
    }

    // We can use the @RequestBody annotation to bind the request JSON body to an object.
    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student) {
        student.setId(0);      // To force an add, not an update.
        return (this.studentService.save(student));
    }

    @PutMapping("/students")
    public Student updateStudent(@RequestBody Student student) {
        return (this.studentService.save(student));
    }

    @DeleteMapping("/students/{student_id}")
    public String deleteStudent(@PathVariable int student_id) {
        Student student = this.studentService.findById(student_id);

        if(student == null) {
            throw new RuntimeException("Student id not found - " + student_id);
        }
        this.studentService.deleteById(student_id);
        return ("Deleted student id - " + student_id);
    }
}
