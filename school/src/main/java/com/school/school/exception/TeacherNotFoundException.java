package com.school.school.exception;

public class TeacherNotFoundException extends RuntimeException {
    public TeacherNotFoundException(String id) {
        super("The teacher with ID: " + id + " cannot been found!");
    }
}
