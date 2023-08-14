package com.school.school.exception;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(String id) {
        super("The course with ID: " + id + " cannot been found!");

    }
}
