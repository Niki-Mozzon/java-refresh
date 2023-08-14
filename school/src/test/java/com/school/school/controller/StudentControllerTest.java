package com.school.school.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.school.school.data.dto.input.StudentInput;
import com.school.school.data.dto.output.StudentOutput;
import com.school.school.data.model.Student;
import com.school.school.utils.StudentGenerator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
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
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql({"/mocks4container.sql"})
class StudentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    private final Student mockStudent = StudentGenerator.generateStudent();
    private final StudentInput mockStudentInput = StudentGenerator.generateStudentInput();


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
    void addStudent() throws Exception {
        StudentInput dto = mockStudentInput;
        String json = mapper.writeValueAsString(dto);
        String contentAsString = mvc.perform(MockMvcRequestBuilders
                        .post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        StudentOutput res = mapper.readValue(contentAsString, StudentOutput.class);
        assertEquals(dto.getName(), res.getName());
        assertEquals(dto.getClassroomId(), res.getClassroomId());
    }

    @Test
    @Order(2)
    void deleteStudent() throws Exception {
        String id = "student1";
        mvc.perform(MockMvcRequestBuilders
                        .delete("/students/delete/" + id))
                .andExpect(status().isOk());
    }


    @Test
    @Order(3)
    void getAllStudents() throws Exception {
        Integer expectedSize = 3;
        String contentAsString = mvc.perform(MockMvcRequestBuilders
                        .get("/students"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<StudentOutput> res = mapper.readValue(contentAsString, new TypeReference<List<StudentOutput>>() {
        });
        assertEquals(3, res.size());
    }

    @Test
    @Order(4)
    void getStudentsByClassroom() throws Exception {
        String classroomId = mockStudent.getClassroomId();
        String contentAsString = mvc.perform(MockMvcRequestBuilders
                        .get("/students/classroom=" + classroomId))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<StudentOutput> res = mapper.readValue(contentAsString, new TypeReference<List<StudentOutput>>() {
        });
        assertEquals(1, res.size());
        assertTrue(res.stream().allMatch(x -> Objects.equals(x.getClassroomId(), classroomId)));
    }

    @Test
    @Order(5)
    void getStudentNamesByClassroom() throws Exception {
        String classroomId = mockStudent.getClassroomId();
        String contentAsString = mvc.perform(MockMvcRequestBuilders
                        .get("/students/names+classroom=" + classroomId))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Map<String, String> res = mapper.readValue(contentAsString, new TypeReference<HashMap<String, String>>() {
        });
        assertEquals(1, res.size());
        assertTrue(res.values().stream().anyMatch(x -> Objects.equals(x, mockStudent.getName())));
    }

    @Test
    @Order(6)
    void getStudentById() throws Exception {
        StudentInput dto = mockStudentInput;
        String contentAsString = mvc.perform(MockMvcRequestBuilders
                        .get("/students/" + mockStudent.getId()))
                .andExpect(status().isFound())
                .andReturn().getResponse().getContentAsString();
        StudentOutput res = mapper.<StudentOutput>readValue(contentAsString, StudentOutput.class);
        assertEquals(dto.getName(), res.getName());
        assertEquals(dto.getClassroomId(), res.getClassroomId());
    }

    @Test
    @Order(7)
    void setClassroomOfStudent_throwExceptionIfIdNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .patch("/students/student=aaa-classroom2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(8)
    void setClassroomOfStudent() throws Exception {
        StudentInput dto = mockStudentInput;
        String classroomId = "classroom3";
        String contentAsString = mvc.perform(MockMvcRequestBuilders
                        .patch("/students/student=" + mockStudent.getId() + "-" + classroomId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        StudentOutput res = mapper.readValue(contentAsString, StudentOutput.class);
        assertEquals(dto.getName(), res.getName());
        assertEquals(classroomId, res.getClassroomId());
    }

    @Order(9)
    @Test
    void setClassroomOfStudents() throws Exception {
        String id1 = "student2";
        String id2 = "student3";
        String classroomId = "classroom1";
        String contentAsString = mvc.perform(MockMvcRequestBuilders
                        .put("/students/classroom=" + classroomId + "?student="+id1+"&student=" + id2))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<StudentOutput> res = mapper.readValue(contentAsString, new TypeReference<List<StudentOutput>>() {
        });
        assertTrue(res.stream().anyMatch(x -> x.getId().equals(id1)));
        assertTrue(res.stream().anyMatch(x -> x.getId().equals(id2)));
        assertTrue(res.stream().allMatch(x -> x.getClassroomId().equals(classroomId)));
    }

    @Order(10)
    @Test
    void setClassroomOfStudents_notFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .put("/students/classroom=zzz?student=yyy&student=xxx"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(11)
    void swapClassroomOfStudents() throws Exception {
        String oldClassroom = "classroom1";
        String newClassroom = "classroom2";
        String contentAsString = mvc.perform(MockMvcRequestBuilders
                        .put("/students/old=" + oldClassroom + "/new=" + newClassroom))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<StudentOutput> res = mapper.readValue(contentAsString, new TypeReference<List<StudentOutput>>() {
        });
        assertEquals(1, res.size());
        assertTrue(res.stream().allMatch(x -> x.getClassroomId().equals(newClassroom)));
    }

    @Order(12)
    @ParameterizedTest()
    @CsvSource({"classroom2,true", "aaa,false"})
    void classroomHasStudents(String id, String expected) throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/students/students-of-classroom=" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }


    @Test
    @Order(13)
    @Sql(statements = "TRUNCATE TABLE public.students CASCADE;")
    void getAllStudents_empty() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/students"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

    }
}