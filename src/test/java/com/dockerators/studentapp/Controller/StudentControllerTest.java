package com.dockerators.studentapp.Controller;
import com.dockerators.studentapp.entity.Student;
import com.dockerators.studentapp.rest.StudentRestController;
import com.dockerators.studentapp.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import static org.mockito.ArgumentMatchers.any;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@WebMvcTest(StudentRestController.class)
public class StudentControllerTest {
    // ObjectMapper to convert objects to JSON and vice versa
    ObjectMapper om = new ObjectMapper();
    @MockBean
    private StudentService studentService;

    @InjectMocks
    private StudentRestController studentRestController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllStudents() throws Exception {
        // Mock data
        Student student1 = new Student(1,"10","Paul", "paul@gmail.com","987654321");
        Student student2 = new Student(1,"20","Adams", "adams@gmail.com","123456789");
        List<Student> students = Arrays.asList(student1, student2);

        // Mock the service method call
        when(studentService.findAll()).thenReturn(students);

        // Perform GET request and validate the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/students")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Paul"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Adams"));

    }

    @Test
    public void testGetStudentFromID() throws Exception {
        // Mock data
        Student student = new Student(1,"10","Paul", "paul@gmail.com","987654321");

        // Mock the service method call
        when(studentService.findById(1)).thenReturn(student);

        // Perform GET request and validate the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/students/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Paul"));

    }

    @Test
    public void testAddStudent() throws Exception {
        // Mock data
        Student student = new Student(0, "10", "Paul", "paul@gmail.com", "987654321");

        // Mock the service method call
        when(studentService.save(student)).thenReturn(student);

        // Perform POST request and validate the response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(student)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Paul"));
    }


    @Test
    public void testUpdateStudent() throws Exception {
        // Mock data
        Student student = new Student(1, "10", "John", "john@example.com", "123456789");

        // Mock the service method call
        when(studentService.save(any(Student.class))).thenReturn(student);

        // Perform PUT request and validate the response
        mockMvc.perform(MockMvcRequestBuilders.put("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(student)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("john@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("123456789"));

        // Verify the service method call
        Mockito.verify(studentService).save(student);
    }



    @Test
    public void testDeleteStudent() throws Exception {
        // Create a student for deletion
        int studentId = 1;
        Student student = new Student(studentId, "10", "John", "john@example.com", "123456789");

        // Mock the service method calls
        when(studentService.findById(studentId)).thenReturn(student);
        doNothing().when(studentService).deleteById(studentId);

        // Perform DELETE request and validate the response
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/students/{student_id}", studentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Deleted student id - " + studentId));

        // Verify the service method calls
        Mockito.verify(studentService).findById(studentId);
        Mockito.verify(studentService).deleteById(studentId);
    }

}
