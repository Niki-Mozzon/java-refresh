package com.school.school.transformer;

import com.school.school.data.dto.output.ExamOutput;
import com.school.school.data.model.Exam;
import com.school.school.utils.ExamGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ExamTransformerTest {

    @Autowired
    private ExamTransformer sut;

    @Test
    void modelToOutput() {
        ExamOutput dto = sut.modelToOutput(ExamGenerator.generateExam());
        assertEquals(ExamGenerator.generateExamOutput(), dto);
    }

    @Test
    void inputToModel() {
        Exam model = ExamGenerator.generateExam();
        Exam dto = sut.inputToModel(ExamGenerator.generateExamInput());
        assertAll(
                () -> model.getRate().equals(dto.getRate()),
                () -> model.getId().getCourse().equals(dto.getId().getCourse()),
                () -> model.getId().getStudent().equals(dto.getId().getStudent())
        );
    }
}