package com.school.classroom.service;

import com.school.classroom.dto.ClassroomInputDto;
import com.school.classroom.dto.ClassroomOutputDto;
import com.school.classroom.exception.ClassroomNotFoundException;
import com.school.classroom.model.Classroom;
import com.school.classroom.repository.ClassroomRepository;
import com.school.classroom.utils.ClassroomGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ClassroomServiceTest {

    @MockBean
    private ClassroomRepository repo;
    @Autowired
    private ClassroomService sut;

    @Test
    void getClassroomById_NotFound() throws ClassroomNotFoundException {
        String id = "0";
        when(repo.findById(id)).thenReturn(Optional.empty());
        assertThrows(ClassroomNotFoundException.class, () -> sut.getClassroomById(id));
    }

    @Test
    void getClassroomById() throws ClassroomNotFoundException {
        ClassroomOutputDto expected = ClassroomGenerator.generateClassroomOutput();
        when(repo.findById(expected.getId())).thenReturn(Optional.ofNullable(ClassroomGenerator.generateClassroom()));
        assertEquals(expected, sut.getClassroomById(expected.getId()));
    }

    @Test
    void getAllClassrooms() {
        when(repo.findAll()).thenReturn(List.of(ClassroomGenerator.generateClassroom(),ClassroomGenerator.generateClassroom()));
        assertEquals(2, sut.getAllClassrooms().size());
    }

    @Test
    void findByNameContaining() {
        String infix = "lia";
        when(repo.findByNameContaining(infix)).thenReturn(List.of(ClassroomGenerator.generateClassroom()));
        assertEquals(1,sut.findByNameContaining(infix).size());
    }

    @Test
    void addClassroom() {
        ClassroomOutputDto expected = ClassroomGenerator.generateClassroomOutput();
        ClassroomInputDto input = ClassroomGenerator.generateClassroomInput();
        Classroom model = ClassroomGenerator.generateClassroom();
        when(repo.save(any())).thenReturn(model);
        assertEquals(expected,sut.addClassroom(input));
    }

    @Test
    void deleteClassroomById_notFound() {

        when(repo.existsById("0")).thenReturn(false);
        assertThrows(ClassroomNotFoundException.class,()->sut.deleteClassroomById("0"));
    }
}