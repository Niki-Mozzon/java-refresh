package com.school.school.exception;

public class ExamNotFoundException extends RuntimeException{
    public ExamNotFoundException(String idStudent,String idCourse) {
        super("The exam of student " + idStudent + " and course "+idCourse+" cannot been found!");

    }
}
