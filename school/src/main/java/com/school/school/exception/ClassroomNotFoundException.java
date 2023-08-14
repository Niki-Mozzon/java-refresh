package com.school.school.exception;

public class ClassroomNotFoundException extends RuntimeException {
    public ClassroomNotFoundException(String id) {
        super("The classroom with ID: " + id + " cannot been found!");

    }
}
