package com.school.school.service.impl;

import com.school.school.data.dto.input.CourseInput;
import com.school.school.data.dto.output.CourseOutput;
import com.school.school.data.model.Course;
import com.school.school.exception.CourseNotFoundException;
import com.school.school.exception.StudentNotFoundException;
import com.school.school.repository.CourseRepository;
import com.school.school.service.CourseService;
import com.school.school.transformer.CourseTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    @Autowired
    private final CourseRepository courseRepository;
    @Autowired
    private final CourseTransformer courseTransformer;

    @Override
    public CourseOutput getCourseById(String id) {

        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) {
            throw new CourseNotFoundException(id);
        }
        return courseTransformer.modelToOutput(course);
    }

    @Override
    public List<CourseOutput> getAllCourses() {

        return courseRepository.findAll().stream().map(courseTransformer::modelToOutput).toList();
    }

    @Override
    public CourseOutput addCourse(CourseInput input) {
        Course modelInput = courseTransformer.inputToModel(input);
        Course model = courseRepository.save(modelInput);
        return courseTransformer.modelToOutput(model);
    }

    @Override
    public Boolean deleteCourse(String id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return !courseRepository.existsById(id);
        }
        throw new CourseNotFoundException(id);
    }
}
