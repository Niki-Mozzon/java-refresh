package com.school.school.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.school.data.dto.input.TeacherInput;
import com.school.school.data.dto.output.TeacherOutput;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@Sql({"/mocks4container.sql"})
@AutoConfigureMockMvc
class TeacherControllerTest {

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
    void getTeacherById() throws Exception {
        String id = "teacher1";
        String contentAsString = mvc.perform(MockMvcRequestBuilders
                        .get("/teachers/" + id))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        TeacherOutput res = mapper.readValue(contentAsString, TeacherOutput.class);
        TeacherOutput exp = TeacherGenerator.generateTeacherOutput();
        assertEquals(exp, res);
    }

    @Test
    void getTeacherById_notFound() throws Exception {
        String id = "maestro1";
        String contentAsString = mvc.perform(MockMvcRequestBuilders
                        .get("/teachers/" + id))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    void getAllTeachers() throws Exception {
        String contentAsString = mvc.perform(MockMvcRequestBuilders.get("/teachers"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<TeacherOutput> res = mapper.readValue(contentAsString, new TypeReference<>() {
        });
        assertEquals(3, res.size());
    }

    @Test
    void addTeacher() throws Exception {
        TeacherOutput exp = TeacherGenerator.generateTeacherOutput();
        String json = mapper.writeValueAsString(TeacherGenerator.generateTeacherInput());
        String contentAsString = mvc.perform(MockMvcRequestBuilders.post("/teachers").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        TeacherOutput res = mapper.readValue(contentAsString, TeacherOutput.class);
        exp.setId(res.getId());
        assertEquals(exp, res);

    }

    @Test
    void deleteTeacher() throws Exception {
        String id= "teacher1";
        mvc.perform(MockMvcRequestBuilders.delete("/teachers/"+id)).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString().equals("true");
    }

    @Test
    void deleteTeacher_notFound() throws Exception {
        String id= "maestro1";
        mvc.perform(MockMvcRequestBuilders.delete("/teachers/"+id)).andExpect(status().isNotFound());

    }
}