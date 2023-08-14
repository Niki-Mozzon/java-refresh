package com.school.school.service;

import com.school.school.data.dto.input.CourseInput;
import com.school.school.data.dto.output.CourseOutput;

import java.util.List;

public interface CourseService {

    CourseOutput getCourseById(String id);
    List<CourseOutput> getAllCourses();
    CourseOutput addCourse(CourseInput input);
    Boolean deleteCourse(String id);

}
