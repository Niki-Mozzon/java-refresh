package com.school.classroom.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AppExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ClassroomNotFoundException.class})
    public Map<String,String>  handleClassroomNotFoundException(ClassroomNotFoundException exc){
        Map<String,String> error = new HashMap<>();
        error.put("errorMessage",exc.getMessage());
        return error;
    }


    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({ClassroomHasStudentsException.class})
    public Map<String,Object>  handleClassroomHasStudentsException(ClassroomHasStudentsException exc){
        Map<String,Object> error = new HashMap<>();
        error.put("errorMessage",exc.getMessage());
        error.put("studentsToRemove",exc.getStudentsToRemove());
        return error;
    }




}
