package com.school.school.exception;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String id) {
        super("The student with ID: " + id + " cannot been found!");

    }
}
