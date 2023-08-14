package com.school.classroom.exception;

import lombok.Data;
import lombok.Getter;

import java.util.Map;
import java.util.Set;

public class ClassroomHasStudentsException extends RuntimeException {

    @Getter
    private Map<String, String> studentsToRemove;

    public ClassroomHasStudentsException(String s, Map<String, String> studentsInClassroom) {
        super(s);
        studentsToRemove = studentsInClassroom;
    }
}
