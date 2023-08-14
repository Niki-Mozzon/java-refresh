package com.school.school.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.school.data.dto.output.CourseOutput;
import com.school.school.utils.CourseGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@Sql({"/mocks4container.sql"})
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class CourseControllerTest {

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
    @Order(1)
    void getCourseById() throws Exception {
        CourseOutput expected = CourseGenerator.generateCourseOutput();
        String contentAsString = mvc.perform(MockMvcRequestBuilders
                        .get("/courses/course1"))
                .andExpect(status()
                        .isOk())
                .andReturn().getResponse().getContentAsString();
        CourseOutput res = mapper.readValue(contentAsString, CourseOutput.class);
        assertEquals(expected, res);
    }

    @Test
    @Order(2)
    void getCourseById_notFound() throws Exception {
        String contentAsString = mvc.perform(MockMvcRequestBuilders
                        .get("/courses/corso1"))
                .andExpect(status()
                        .isNotFound())
                .andReturn().getResponse()
                .getContentAsString();
    }

    @Test
    @Order(3)
    void getAllCourses() throws Exception {
        Integer expected = 3;
        String contentAsString = mvc.perform(MockMvcRequestBuilders
                        .get("/courses"))
                .andExpect(status()
                        .isOk())
                .andReturn().getResponse().getContentAsString();
        List<CourseOutput> res = mapper.readValue(contentAsString, new TypeReference<>() {
        });
        assertEquals(expected, res.size());
    }

    @Test
    @Order(4)
    void addCourse() throws Exception {
        CourseOutput expected = CourseGenerator.generateCourseOutput();
        String contentAsString = mvc.perform(MockMvcRequestBuilders
                        .post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(CourseGenerator.generateCourseInput())))
                .andExpect(status()
                        .isOk())
                .andReturn().getResponse().getContentAsString();
        CourseOutput res = mapper.readValue(contentAsString, CourseOutput.class);
        expected.setId(res.getId());
        assertEquals(expected, res);
    }


    @Test
    @Order(5)
    void deleteCourse() throws Exception {
        String id = "course1";
        mvc.perform(MockMvcRequestBuilders
                        .delete("/courses/" + id))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString()
                .equals("true");

    }

    @Test
    @Order(6)
    void deleteCourse_notFound() throws Exception {
        String id = "abc";
        mvc.perform(MockMvcRequestBuilders
                        .delete("/courses/" + id))
                .andExpect(status()
                        .isNotFound());
    }
}