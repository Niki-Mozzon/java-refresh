package com.school.school.transformer;

import com.school.school.data.dto.output.CourseOutput;
import com.school.school.data.model.Course;
import com.school.school.utils.CourseGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseTransformerTest {

    @Autowired
    private CourseTransformer sut;

    @Test
    void modelToOutput() {
        CourseOutput dto = sut.modelToOutput(CourseGenerator.generateCourse());
        assertEquals(CourseGenerator.generateCourseOutput(), dto);
    }

    @Test
    void inputToModel() {
        Course model = CourseGenerator.generateCourse();
        Course dto = sut.inputToModel(CourseGenerator.generateCourseInput());
        assertAll(
                () -> model.getTeacher().equals(dto.getTeacher()),
                () -> model.getName().equals(dto.getName())
        );
    }
}