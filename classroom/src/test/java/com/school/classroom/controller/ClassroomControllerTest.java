package com.school.classroom.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.classroom.dto.ClassroomInputDto;
import com.school.classroom.dto.ClassroomOutputDto;
import com.school.classroom.repository.StudentRestConnector;
import com.school.classroom.utils.ClassroomGenerator;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@Log4j2
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClassroomControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    private static String id;
    @MockBean
    private StudentRestConnector mockConnector;
    @Container
    private static MongoDBContainer db = new MongoDBContainer("mongo");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", db::getReplicaSetUrl);
    }


    @Test
    @Order(1)
    void addClassroom() throws Exception {
        ClassroomInputDto input = ClassroomGenerator.generateClassroomInput();
        String contentAsString = mvc.perform(MockMvcRequestBuilders
                        .post("/classrooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input))
                ).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        ClassroomOutputDto output = mapper.readValue(contentAsString, ClassroomOutputDto.class);
        assertAll(
                () -> assertEquals(input.getName(), output.getName())
        );
        id = output.getId();
        log.info("ID = " + id);
    }

    @Test
    @Order(2)
    void getAllClassrooms() throws Exception {
        ClassroomOutputDto expected = ClassroomGenerator.generateClassroomOutput();
        String contentAsString = mvc.perform(MockMvcRequestBuilders
                        .get("/classrooms")
                ).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<ClassroomOutputDto> outputs = mapper.readValue(contentAsString, new TypeReference<List<ClassroomOutputDto>>() {
        });
        ClassroomOutputDto output = outputs.stream().findFirst().get();
        assertAll(
                () -> assertEquals(1, outputs.size()),
                () -> assertEquals(expected.getName(), output.getName()),
                () -> assertEquals(id, output.getId())
        );

    }

    @Test
    @Order(3)
    void getClassroomById() throws Exception {
        String contentAsString = mvc.perform(MockMvcRequestBuilders
                        .get("/classrooms/" + id))
                .andExpect(status().isFound())
                .andReturn().getResponse().getContentAsString();
        ClassroomOutputDto output = mapper.readValue(contentAsString, ClassroomOutputDto.class);
        assertEquals(ClassroomGenerator.generateClassroomOutput().getName(), output.getName());
    }

    @Order(4)
    @Test
    void getClassroomById_NotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/classrooms/00"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(5)
    void findByNameContaining() throws Exception {
        String contentAsString = mvc.perform(MockMvcRequestBuilders
                        .get("/classrooms/query=ali"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<ClassroomOutputDto> outputs = mapper.readValue(contentAsString, new TypeReference<List<ClassroomOutputDto>>() {
        });
        assertAll(
                () -> assertEquals(1, outputs.size())
        );
    }

    @Test
    @Order(6)
    void deleteClassroomById_notFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .delete("/classrooms/delete/000"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(7)
    void deleteClassroomById() throws Exception {
        when(mockConnector.haveStudentsInClassroom(id)).thenReturn(false);
        mvc.perform(MockMvcRequestBuilders
                        .delete("/classrooms/delete/" + id))
                .andExpect(status().isOk());
    }

    @Test
    @Order(8)
    void getAllClassrooms_isEmpty() throws Exception {
        ClassroomOutputDto expected = ClassroomGenerator.generateClassroomOutput();
        mvc.perform(MockMvcRequestBuilders
                        .get("/classrooms")
                ).andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}