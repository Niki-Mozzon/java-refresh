package com.school.school.controller;

import com.school.school.data.dto.input.ExamInput;
import com.school.school.data.dto.output.ExamOutput;
import com.school.school.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/exams")
@RestController
public class ExamController {
    @Autowired
    private ExamService examService;

    @GetMapping("/{idStudent}+{idCourse}")
    ResponseEntity<ExamOutput> getExamById(@PathVariable String idStudent, @PathVariable String idCourse) {
        return ResponseEntity.ok(examService.getExamById(idStudent, idCourse));
    }

    @GetMapping
    ResponseEntity<List<ExamOutput>> getAllExams(){
        return ResponseEntity.ok(examService.getAllExams());
    }

    @GetMapping("/student={idStudent}")
    ResponseEntity<List<ExamOutput>> getAllExamsByStudent(@PathVariable String idStudent){
        return ResponseEntity.ok(examService.getAllExamsByStudent(idStudent));
    }

    @GetMapping("/course={idCourse}")
    ResponseEntity<List<ExamOutput>> getAllExamsByCourse(@PathVariable String idCourse){
        return ResponseEntity.ok(examService.getAllExamsByCourse(idCourse));
    }

    @PostMapping
    ResponseEntity<ExamOutput> addExam(@RequestBody ExamInput input){
        return ResponseEntity.ok(examService.addExam(input));
    }

    @DeleteMapping("/{idStudent}+{idCourse}")
    ResponseEntity<Boolean> deleteExam(@PathVariable String idStudent, @PathVariable String idCourse){
        return ResponseEntity.ok(examService.deleteExam(idStudent,idCourse));
    }
}
