package com.school.classroom.repository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Disabled("Requires API request to school microservice!")
@SpringBootTest
class StudentConnectorTest {

    @Autowired
    private StudentRestConnector connector;

    @Test
    void haveStudentsInClassroom() throws URISyntaxException, IOException, InterruptedException {
        assertTrue(connector.haveStudentsInClassroom("64ca75e1e2eddf189e08a4c0"));
    }

    @Test
    void studentsInClassroom() throws URISyntaxException, IOException, InterruptedException {
        Map<String, String> students = connector.studentsInClassroom("64ca75e1e2eddf189e08a4c0");
        assertEquals(2, students.size());
    }

    @Test
    void studentsInClassroomRequest() throws URISyntaxException, IOException, InterruptedException {
        Map<String, String> students = connector.studentsInClassroom("64ca75e1e2eddf189e08a4c0");
        assertEquals(2, students.size());
    }


}