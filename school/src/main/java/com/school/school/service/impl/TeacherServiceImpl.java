package com.school.school.service.impl;

import com.school.school.data.dto.input.TeacherInput;
import com.school.school.data.dto.output.TeacherOutput;
import com.school.school.data.model.Teacher;
import com.school.school.exception.TeacherNotFoundException;
import com.school.school.repository.TeacherRepository;
import com.school.school.service.TeacherService;
import com.school.school.transformer.TeacherTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private final TeacherRepository teacherRepository;
    @Autowired
    private TeacherTransformer transformer;

    @Override
    public TeacherOutput getTeacherById(String id) {
        Teacher teacher = teacherRepository.findById(id).orElse(null);
        if (teacher == null) {
            throw new TeacherNotFoundException(id);
        }
        return transformer.modelToOutput(teacher);
    }

    @Override
    public List<TeacherOutput> getAllTeachers() {
        return teacherRepository.findAll().stream().map(transformer::modelToOutput).toList();
    }

    @Override
    public TeacherOutput addTeacher(TeacherInput input) {
        return transformer.modelToOutput(teacherRepository.save(transformer.inputToModel(input)));
    }

    @Override
    public boolean deleteTeacher(String id) {
        boolean exists = teacherRepository.existsById(id);
        if (exists) {
            teacherRepository.removeTeacherFromCourses(id);
            teacherRepository.deleteById(id);
            return !teacherRepository.existsById(id);
        }
        throw new TeacherNotFoundException(id);
    }
}
