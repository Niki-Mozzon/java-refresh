package com.school.classroom.transformer;

import com.school.classroom.dto.ClassroomOutputDto;
import com.school.classroom.model.Classroom;
import com.school.classroom.utils.ClassroomGenerator;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClassroomTransformerTest {

    @Autowired
    private ClassroomTransformer sut;

    @Test
    void modelToOutput() {
        ClassroomOutputDto output = sut.modelToOutput(ClassroomGenerator.generateClassroom());
        assertEquals(ClassroomGenerator.generateClassroomOutput(),output);
    }

    @Test
    void inputToModel() {
        Classroom model = sut.inputToModel(ClassroomGenerator.generateClassroomInput());
        assertEquals(ClassroomGenerator.generateClassroom().getName(),model.getName());
    }
}