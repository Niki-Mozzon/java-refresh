package com.school.school.service;

import com.school.school.data.dto.input.TeacherInput;
import com.school.school.data.dto.output.TeacherOutput;
import com.school.school.data.model.Teacher;
import com.school.school.exception.TeacherNotFoundException;
import com.school.school.repository.TeacherRepository;
import com.school.school.utils.TeacherGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class TeacherServiceTest {

    @Autowired
    TeacherService sut;
    @MockBean
    TeacherRepository repo;


    @Test
    void getTeacherById() {
        TeacherOutput exp = TeacherGenerator.generateTeacherOutput();
        when(repo.findById("abc")).thenReturn(Optional.ofNullable(TeacherGenerator.generateTeacher()));
        TeacherOutput res = sut.getTeacherById("abc");
        assertEquals(exp, res);
    }

    @Test
    void getTeacherById_notFound() {
        TeacherOutput exp = TeacherGenerator.generateTeacherOutput();
        when(repo.findById("abc")).thenReturn(Optional.empty());
        assertThrows(TeacherNotFoundException.class, () -> sut.getTeacherById("abc"));
    }

    @Test
    void getAllTeachers() {
        when(repo.findAll()).thenReturn(List.of(TeacherGenerator.generateTeacher(), TeacherGenerator.generateTeacher()));
        List<TeacherOutput> res = sut.getAllTeachers();
        assertAll(
                () -> assertEquals(2, res.size()),
                () -> assertTrue(res.stream().allMatch(x -> x.equals(TeacherGenerator.generateTeacherOutput())))
        );
    }

    @Test
    void addTeacher() {
        Teacher model = TeacherGenerator.generateTeacher();
        Teacher modelOutput = TeacherGenerator.generateTeacher();
        TeacherInput input = TeacherGenerator.generateTeacherInput();
        TeacherOutput exp = TeacherGenerator.generateTeacherOutput();
        model.setId(null);
        when(repo.save(model)).thenReturn(modelOutput);
        TeacherOutput res = sut.addTeacher(input);
        assertEquals(exp, res);
    }

    @Test
    void deleteTeacher() {
        when(repo.existsById("abc")).thenReturn(true).thenReturn(false);
        boolean res = sut.deleteTeacher("abc");
        assertTrue(res);
    }

    @Test
    void deleteTeacher_notFound() {
        when(repo.existsById("abc")).thenReturn(false);
        assertThrows(TeacherNotFoundException.class, () -> sut.deleteTeacher("abc"));
    }
}