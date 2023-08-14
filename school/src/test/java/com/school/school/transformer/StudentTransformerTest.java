package com.school.school.transformer;

import com.school.school.data.dto.output.StudentOutput;
import com.school.school.data.model.Student;
import com.school.school.utils.StudentGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentTransformerTest {

    @Autowired
    private StudentTransformer sut;

    @Test
    void modelToOutput() {
        StudentOutput dto = sut.modelToOutput(StudentGenerator.generateStudent());
        assertEquals(StudentGenerator.generateStudentOutput(), dto);
    }

    @Test
    void inputToModel() {
        Student model = StudentGenerator.generateStudent();
        Student dto = sut.inputToModel(StudentGenerator.generateStudentInput());
        assertAll(
                () -> model.getClassroomId().equals(dto.getClassroomId()),
                () -> model.getName().equals(dto.getName())
        );
    }
}