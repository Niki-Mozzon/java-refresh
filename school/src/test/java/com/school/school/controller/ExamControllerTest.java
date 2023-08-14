package com.school.school.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.school.data.dto.input.ExamInput;
import com.school.school.data.dto.output.ExamOutput;
import com.school.school.data.dto.output.TeacherOutput;
import com.school.school.utils.ExamGenerator;
import com.school.school.utils.TeacherGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@Sql({"/mocks4container.sql"})
@AutoConfigureMockMvc
class ExamControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Container
    private static PostgreSQLContainer sqlContainer = new PostgreSQLContainer("postgres")
            .withDatabaseName("school-integ")
            .withUsername("test")
            .withPassword("testpass");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", sqlContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", sqlContainer::getPassword);
    }

    @Test
    void getExamById() throws Exception {
        String studentId = "student1";
        String courseId = "course1";
        String contentAsString = mvc.perform(MockMvcRequestBuilders
                        .get("/exams/" + studentId + "+" + courseId))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        ExamOutput res = mapper.readValue(contentAsString, ExamOutput.class);
        ExamOutput exp = ExamGenerator.generateExamOutput();
        assertEquals(exp, res);
    }

    @Test
    void getExamById_notFound() throws Exception {
        String studentId = "studente1";
        String courseId = "corso1";
        mvc.perform(MockMvcRequestBuilders
                        .get("/exams/" + studentId + "+" + courseId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllExams() throws Exception {
        String contentAsString = mvc.perform
                        (MockMvcRequestBuilders
                                .get("/exams"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<ExamOutput> res = mapper.readValue(contentAsString, new TypeReference<List<ExamOutput>>() {
        });
        assertEquals(6, res.size());
    }

    @Test
    void getAllExamsByStudent() throws Exception {
        String id = "student1";
        String contentAsString = mvc.perform
                        (MockMvcRequestBuilders
                                .get("/exams/student=" + id))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<ExamOutput> res = mapper.readValue(contentAsString, new TypeReference<List<ExamOutput>>() {
        });
        assertEquals(2, res.size());
    }

    @Test
    void getAllExamsByCourse() throws Exception {
        String id = "course1";
        String contentAsString = mvc.perform
                        (MockMvcRequestBuilders
                                .get("/exams/course=" + id))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<ExamOutput> res = mapper.readValue(contentAsString, new TypeReference<List<ExamOutput>>() {
        });
        assertEquals(2, res.size());
    }

    @Test
    void addExam() throws Exception {
        ExamInput input = new ExamInput("student3", "course1", 8.5);
        ExamOutput exp = new ExamOutput("student3", "course1", 8.5);
        String json = mapper.writeValueAsString(input);
        String contentAsString = mvc.perform(MockMvcRequestBuilders
                        .post("/exams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        ExamOutput res = mapper.readValue(contentAsString, ExamOutput.class);
        assertEquals(exp, res);
    }

    @Test
    void deleteExam() throws Exception {
        String studentId = "student1";
        String courseId = "course1";
        mvc.perform(MockMvcRequestBuilders
                        .delete("/exams/" + studentId + "+" + courseId))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString().equals("true");
    }

    @Test
    void deleteExam_notFound() throws Exception {
        String studentId = "studente1";
        String courseId = "corso1";
        mvc.perform(MockMvcRequestBuilders
                        .delete("/exams/" + studentId + "+" + courseId))
                .andExpect(status().isNotFound());
    }
}