package com.school.school.service;

import com.school.school.data.dto.input.CourseInput;
import com.school.school.data.dto.output.CourseOutput;
import com.school.school.data.model.Course;
import com.school.school.exception.CourseNotFoundException;
import com.school.school.repository.CourseRepository;
import com.school.school.service.CourseService;
import com.school.school.utils.CourseGenerator;
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
class CourseServiceTest {

    @Autowired
    private CourseService sut;
    @MockBean
    private CourseRepository repo;


    @Test
    void getCourseById() {
        CourseOutput expected = CourseGenerator.generateCourseOutput();
        when(repo.findById("abc")).thenReturn(Optional.ofNullable(CourseGenerator.generateCourse()));
        CourseOutput res = sut.getCourseById("abc");
        assertEquals(expected, res);

    }

    @Test
    void getCourseById_notFound() {
        when(repo.findById(any())).thenReturn(Optional.empty());
        assertThrows(CourseNotFoundException.class, () -> sut.getCourseById("abc"));
    }

    @Test
    void getAllCourses() {
        CourseOutput expected = CourseGenerator.generateCourseOutput();
        when(repo.findAll()).thenReturn(List.of(CourseGenerator.generateCourse(), CourseGenerator.generateCourse()));
        List<CourseOutput> res = sut.getAllCourses();
        assertEquals(2, res.size());
        assertTrue(res.stream().allMatch(x -> x.equals(expected)));
    }

    @Test
    void addCourse() {
        CourseOutput expected = CourseGenerator.generateCourseOutput();
        CourseInput input = CourseGenerator.generateCourseInput();
        Course model = CourseGenerator.generateCourse();
        Course modelOutput = CourseGenerator.generateCourse();
        model.setId(null);
        when(repo.save(model)).thenReturn(modelOutput);
        CourseOutput res = sut.addCourse(input);
        assertEquals(expected, res);

    }

    @Test
    void deleteCourse() {
        when(repo.existsById("abc")).thenReturn(true).thenReturn(false);
        Boolean res = sut.deleteCourse("abc");
        assertTrue(res);
    }

    @Test
    void deleteCourse_notFound() {
        when(repo.existsById("abc")).thenReturn(false);
        assertThrows(CourseNotFoundException.class, () -> sut.deleteCourse("abc"));
    }
}