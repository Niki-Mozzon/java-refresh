package com.school.school.service;

import com.school.school.data.dto.input.ExamInput;
import com.school.school.data.dto.output.ExamOutput;
import com.school.school.data.model.Exam;
import com.school.school.data.model.key.ExamKey;
import com.school.school.exception.ExamNotFoundException;
import com.school.school.repository.ExamRepository;
import com.school.school.utils.ExamGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ExamServiceTest {

    @Autowired
    ExamService sut;
    @MockBean
    ExamRepository repo;

    @Test
    void getExamById() {
        ExamKey examKey = new ExamKey("123", "abc");
        when(repo.findById(examKey)).thenReturn(Optional.ofNullable(ExamGenerator.generateExam()));
        ExamOutput exp = ExamGenerator.generateExamOutput();
        ExamOutput res = sut.getExamById("abc", "123");
        assertEquals(exp, res);
    }

    @Test
    void getExamById_notFound() {
        when(repo.findById(new ExamKey("123", "abc"))).thenReturn(Optional.empty());
        assertThrows(ExamNotFoundException.class, () -> sut.getExamById("abc", "123"));
    }

    @Test
    void getAllExams() {
        when(repo.findAll()).thenReturn(List.of(ExamGenerator.generateExam(), ExamGenerator.generateExam()));
        List<ExamOutput> res = sut.getAllExams();
        assertAll(
                () -> assertEquals(2, res.size()),
                () -> assertTrue(res.stream().allMatch(x -> x.equals(ExamGenerator.generateExamOutput())))
        );
    }

    @Test
    void getAllExamsOfStudent() {
        when(repo.findAllExamsByStudent("abc")).thenReturn(List.of(ExamGenerator.generateExam(), ExamGenerator.generateExam()));
        List<ExamOutput> res = sut.getAllExamsByStudent("abc");
        assertAll(
                () -> assertEquals(2, res.size()),
                () -> assertTrue(res.stream().allMatch(x -> x.equals(ExamGenerator.generateExamOutput())))
        );
    }

    @Test
    void getAllExamsOfCourse() {
        when(repo.findAllExamsByCourse("123")).thenReturn(List.of(ExamGenerator.generateExam(), ExamGenerator.generateExam()));
        List<ExamOutput> res = sut.getAllExamsByCourse("123");
        assertAll(
                () -> assertEquals(2, res.size()),
                () -> assertTrue(res.stream().allMatch(x -> x.equals(ExamGenerator.generateExamOutput())))
        );
    }

    @Test
    void addExam() {
        Exam model = ExamGenerator.generateExam();
        ExamInput input = ExamGenerator.generateExamInput();
        ExamOutput exp = ExamGenerator.generateExamOutput();
        when(repo.save(model)).thenReturn(model);
        ExamOutput res = sut.addExam(input);
        assertEquals(exp,res);
    }

    @Test
    void deleteExam() {
        ExamKey id = new ExamKey("123", "abc");
        when(repo.existsById(id)).thenReturn(true).thenReturn(false);
        assertTrue(sut.deleteExam(id.getStudent(), id.getCourse()));
    }

    @Test
    void deleteExam_notFound() {
        ExamKey id = new ExamKey("123", "abc");
        when(repo.existsById(id)).thenReturn(false);
        assertThrows(ExamNotFoundException.class,()->sut.deleteExam(id.getStudent(), id.getCourse()));

    }
}