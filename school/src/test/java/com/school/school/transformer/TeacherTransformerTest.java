package com.school.school.transformer;

import com.school.school.data.dto.output.TeacherOutput;
import com.school.school.data.model.Teacher;
import com.school.school.utils.TeacherGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeacherTransformerTest {
    @Autowired
    private TeacherTransformer sut;

    @Test
    void modelToOutput() {
        TeacherOutput dto = sut.modelToOutput(TeacherGenerator.generateTeacher());
        assertEquals(TeacherGenerator.generateTeacherOutput(), dto);
    }

    @Test
    void inputToModel() {
        Teacher model = TeacherGenerator.generateTeacher();
        Teacher dto = sut.inputToModel(TeacherGenerator.generateTeacherInput());
        assertAll(
                () -> model.getId().equals(dto.getId()),
                () -> model.getName().equals(dto.getName())
        );
    }

}