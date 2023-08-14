package com.school.school.utils;

import com.school.school.data.dto.input.StudentInput;
import com.school.school.data.dto.output.StudentOutput;
import com.school.school.data.model.Student;

public class StudentGenerator {

    public static Student generateStudent(){
        return Student.builder()
                .id("student1")
                .name("Pele")
                .classroomId("classroom1")
                .build();
    }

    public static StudentOutput generateStudentOutput(){
        return StudentOutput.builder()
                .id("student1")
                .name("Pele")
                .classroomId("classroom1")
                .build();
    }
    public static StudentInput generateStudentInput(){
        return StudentInput.builder()
                .name("Pele")
                .classroomId("classroom1")
                .build();
    }
}
