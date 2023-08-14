package com.school.classroom.service;

import com.school.classroom.dto.ClassroomInputDto;
import com.school.classroom.dto.ClassroomOutputDto;
import com.school.classroom.exception.ClassroomNotFoundException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface ClassroomService {
    public ClassroomOutputDto getClassroomById(String id) throws ClassroomNotFoundException;
    public List<ClassroomOutputDto> getAllClassrooms();
    public List<ClassroomOutputDto> findByNameContaining(String infix);
    public ClassroomOutputDto addClassroom(ClassroomInputDto input);
    public void deleteClassroomById(String id) throws ClassroomNotFoundException, URISyntaxException, IOException, InterruptedException;

}
