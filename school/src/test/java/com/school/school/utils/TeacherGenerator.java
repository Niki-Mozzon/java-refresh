package com.school.school.utils;

import com.school.school.data.dto.input.TeacherInput;
import com.school.school.data.dto.output.TeacherOutput;
import com.school.school.data.model.Teacher;
import com.school.school.data.model.Teacher;

public class TeacherGenerator {
    public static Teacher generateTeacher(){
        return Teacher.builder()
                .id("teacher1")
                .name("Ronaldo")
                .build();
    }

    public static TeacherOutput generateTeacherOutput(){
        return TeacherOutput.builder()
                .id("teacher1")
                .name("Ronaldo")
                .build();
    }
    public static TeacherInput generateTeacherInput(){
        return TeacherInput.builder()
                .name("Ronaldo")
                .build();
    }
}
