package com.school.school.controller;

import com.school.school.data.dto.input.CourseInput;
import com.school.school.data.dto.output.CourseOutput;
import com.school.school.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/courses")
@RestController
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/{id}")
    ResponseEntity<CourseOutput> getCourseById(@PathVariable String id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @GetMapping
    ResponseEntity<List<CourseOutput>> getAllCourses(){
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @PostMapping
    ResponseEntity<CourseOutput> addCourse(@RequestBody CourseInput input){
        return ResponseEntity.ok(courseService.addCourse(input));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Boolean> deleteCourse(@PathVariable String id){
        return ResponseEntity.ok(courseService.deleteCourse(id));
    }

}
