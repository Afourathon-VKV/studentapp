package com.dockerators.studentapp.exception;

public class StudentNotFoundException extends RuntimeException{
    public StudentNotFoundException() {
        super("A Student With that roll number/id does not exist.");
    }
}
