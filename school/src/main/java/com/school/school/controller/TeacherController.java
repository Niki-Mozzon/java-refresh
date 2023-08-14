package com.school.school.controller;

import com.school.school.data.dto.input.TeacherInput;
import com.school.school.data.dto.output.TeacherOutput;
import com.school.school.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/{id}")
    ResponseEntity<TeacherOutput> getTeacherById(@PathVariable String id) {
        return ResponseEntity.ok(teacherService.getTeacherById(id));
    }

    @GetMapping
    ResponseEntity<List<TeacherOutput>> getAllTeachers() {
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }

    @PostMapping
    ResponseEntity<TeacherOutput> addTeacher(@RequestBody TeacherInput input) {
        return ResponseEntity.ok(teacherService.addTeacher(input));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Boolean> deleteTeacher(@PathVariable String id) {
        return ResponseEntity.ok(teacherService.deleteTeacher(id));
    }

}
