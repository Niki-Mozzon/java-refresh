package com.school.classroom.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class StudentRestConnector {

    @Autowired
    ObjectMapper mapper;

    public Boolean haveStudentsInClassroom(String id) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8081/students/students-of-classroom=" + id))
                .GET()
                .build();
        return Boolean.parseBoolean(HttpClient.newBuilder().build().send(httpRequest, HttpResponse.BodyHandlers.ofString()).body());
    }

    public Map<String, String> studentsInClassroom(String id) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8081/students/names+classroom=" + id))
                .GET()
                .build();
        String body = HttpClient.newBuilder().build().send(httpRequest, HttpResponse.BodyHandlers.ofString()).body();
        return mapper.readValue(body, new TypeReference<HashMap<String, String>>() {
        });
    }
}
