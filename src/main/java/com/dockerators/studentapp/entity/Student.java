package com.dockerators.studentapp.entity;

import jakarta.persistence.*;

import java.util.Objects;
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id; // Primary key of the student table to identify students
    @Column(name = "rollNo")
    private String rollNo; // Roll number of the student

    @Column(name = "name")
    private String name; // Name of the student
    @Column(name = "email")
    private String email; // Email of the student
    @Column(name = "phone")
    private String phone; // Phone number of the student

    // No argument constructor
    public Student() {
    }

    // All argument constructor
    public Student(int id, String rollNo, String name, String email, String phone) {
        this.id = id;
        this.rollNo = rollNo;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Overridden toString() function to return in JSON {} format
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", rollNo='" + rollNo + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
    // Overridden equals function : for controller level tests to mock service functionality
    // Returns true if two objects have the same value for all fields
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.getId()) &&
                Objects.equals(email, student.getEmail()) &&
                Objects.equals(rollNo, student.getRollNo()) &&
                Objects.equals(name, student.getName()) &&
                Objects.equals(phone, student.getPhone());
    }

    // Used in the equals function
    // Overridden to provide a consistent hash code for objects with equal field values.
    @Override
    public int hashCode() {
        return Objects.hash(id, email, rollNo, name, phone);
    }
}
