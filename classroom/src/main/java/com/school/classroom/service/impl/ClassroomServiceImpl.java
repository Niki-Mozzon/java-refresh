package com.school.classroom.service.impl;

import com.school.classroom.dto.ClassroomInputDto;
import com.school.classroom.dto.ClassroomOutputDto;
import com.school.classroom.exception.ClassroomHasStudentsException;
import com.school.classroom.exception.ClassroomNotFoundException;
import com.school.classroom.model.Classroom;
import com.school.classroom.repository.ClassroomRepository;
import com.school.classroom.repository.StudentRestConnector;
import com.school.classroom.service.ClassroomService;
import com.school.classroom.transformer.ClassroomTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {
    @Autowired
    private final ClassroomRepository classroomRepository;
    @Autowired
    private final StudentRestConnector studentConnector;
    @Autowired
    private final ClassroomTransformer transformer;

    @Override
    public ClassroomOutputDto getClassroomById(String id) throws ClassroomNotFoundException {
        Classroom res = classroomRepository.findById(id).orElse(null);
        if (res == null) {
            throw new ClassroomNotFoundException("The classroom with id " + id + " cannot be found!");
        }
        return transformer.modelToOutput(res);
    }

    @Override
    public List<ClassroomOutputDto> getAllClassrooms() {
        return classroomRepository.findAll().stream().map(transformer::modelToOutput).toList();
    }

    @Override
    public List<ClassroomOutputDto> findByNameContaining(String infix) {
        return classroomRepository.findByNameContaining(infix).stream().map(transformer::modelToOutput).toList();
    }

    @Override
    public ClassroomOutputDto addClassroom(ClassroomInputDto input) {
        return transformer.modelToOutput(classroomRepository.save(transformer.inputToModel(input)));
    }

    @Override
    public void deleteClassroomById(String id) throws ClassroomNotFoundException, URISyntaxException, IOException, InterruptedException {
        boolean exists = classroomRepository.existsById(id);
        if (!exists) {
            throw new ClassroomNotFoundException("The classroom with id " + id + " cannot be found therefore cannot be deleted!");
        }
        if (studentConnector.haveStudentsInClassroom(id)){
            Map<String, String> studentsInClassroom = studentConnector.studentsInClassroom(id);
            throw new ClassroomHasStudentsException("The classroom with id " +id +" has students, remove the following students from the classroom before proceeding with these action:",studentsInClassroom);
        }
        classroomRepository.deleteById(id);
    }
}
