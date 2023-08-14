package com.school.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AppExceptionHandler {
    private String errorMessageKey="errorMessage";

    @ExceptionHandler({StudentNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,String> handleStudentNotFoundException(StudentNotFoundException exc){
        Map<String,String> error = new HashMap<>();
        error.put(errorMessageKey,exc.getMessage());
        return error;
    }

    @ExceptionHandler({ClassroomNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,String> handleClassroomNotFoundException(ClassroomNotFoundException exc){
        Map<String,String> error = new HashMap<>();
        error.put(errorMessageKey,exc.getMessage());
        return error;
    }

    @ExceptionHandler({CourseNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,String> handleCourseNotFoundException(CourseNotFoundException exc){
        Map<String,String> error = new HashMap<>();
        error.put(errorMessageKey,exc.getMessage());
        return error;
    }

    @ExceptionHandler({ExamNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,String> handleExamNotFoundException(ExamNotFoundException exc){
        Map<String,String> error = new HashMap<>();
        error.put(errorMessageKey,exc.getMessage());
        return error;
    }

    @ExceptionHandler({TeacherNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,String> handleTeacherNotFoundException(TeacherNotFoundException exc){
        Map<String,String> error = new HashMap<>();
        error.put(errorMessageKey,exc.getMessage());
        return error;
    }
}
