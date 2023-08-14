package com.school.school.utils;

import com.school.school.data.dto.input.CourseInput;
import com.school.school.data.dto.output.CourseOutput;
import com.school.school.data.model.Course;
import com.school.school.data.model.Teacher;

public class CourseGenerator {
    public static Course generateCourse(){
        return Course.builder()
                .id("course1")
                .name("Geography")
                .teacher(Teacher.builder().id("teacher1").build())
                .build();
    }

    public static CourseOutput generateCourseOutput(){
        return CourseOutput.builder()
                .id("course1")
                .name("Geography")
                .teacher("teacher1")
                .build();
    }
    public static CourseInput generateCourseInput(){
        return CourseInput.builder()
                .name("Geography")
                .teacher("teacher1")
                .build();
    }
}
