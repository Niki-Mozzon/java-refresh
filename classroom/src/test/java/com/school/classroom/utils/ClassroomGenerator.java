package com.school.classroom.utils;

import com.school.classroom.dto.ClassroomInputDto;
import com.school.classroom.dto.ClassroomOutputDto;
import com.school.classroom.model.Classroom;

public class ClassroomGenerator {

    public static ClassroomOutputDto generateClassroomOutput() {
        return ClassroomOutputDto.builder()
                .id("abc")
                .name("Italian")
                .build();
    }

    public static Classroom generateClassroom() {
        return Classroom.builder()
                .id("abc")
                .name("Italian")
                .build();
    }
    public static ClassroomInputDto generateClassroomInput() {
        return ClassroomInputDto.builder()
                .name("Italian")
                .build();
    }

}
