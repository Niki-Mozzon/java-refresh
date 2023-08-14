package com.school.school.controller;

import com.school.school.data.dto.input.StudentInput;
import com.school.school.data.dto.output.StudentOutput;
import com.school.school.exception.StudentNotFoundException;
import com.school.school.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    @Autowired
    private final StudentService studentService;

    @GetMapping("/{id}")
    public ResponseEntity<StudentOutput> getStudentById(@PathVariable String id) throws StudentNotFoundException {
        return new ResponseEntity<>(studentService.getStudent(id), HttpStatus.FOUND);
    }

    @GetMapping
    public ResponseEntity<List<StudentOutput>> getAllStudents() {
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
    }

    @GetMapping("/names+classroom={id}")
    public ResponseEntity<Map<String,String>> getStudentNamesByClassroom(@PathVariable String id){
        return new ResponseEntity<>(studentService.getStudentNamesByClassroom(id),HttpStatus.OK);
    }

    @GetMapping("/classroom={id}")
    public ResponseEntity<List<StudentOutput>> getStudentsByClassroom(@PathVariable String id) {
        return new ResponseEntity<>(studentService.getStudentsByClassroom(id), HttpStatus.OK);
    }

    @GetMapping("/students-of-classroom={id}")
    public ResponseEntity<Boolean> classroomHasStudents(@PathVariable String id) {
        return new ResponseEntity<>(studentService.classroomHasStudents(id), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<StudentOutput> addStudent(@RequestBody StudentInput dto)  {
        return new ResponseEntity<>(studentService.addStudent(dto), HttpStatus.CREATED);
    }

    @PatchMapping("/student={studentId}-{classroomId}")
    public ResponseEntity<StudentOutput> setClassroomOfStudent(@PathVariable String studentId, @PathVariable String classroomId) throws StudentNotFoundException {
        return new ResponseEntity<>(studentService.setClassroomOfStudent(studentId,classroomId), HttpStatus.OK);
    }

    @PutMapping("/classroom={classroomId}")
    public ResponseEntity<List<StudentOutput>> setClassroomOfStudents(@PathVariable String classroomId, @RequestParam List<String> student) throws StudentNotFoundException {
        return new ResponseEntity<>(studentService.setClassroomOfStudents(classroomId, student), HttpStatus.OK);
    }

    @PutMapping("/old={oldClassroomId}/new={newClassroomId}")
    public ResponseEntity<List<StudentOutput>> swapClassroomOfStudents(@PathVariable String oldClassroomId, @PathVariable String newClassroomId) throws StudentNotFoundException {
        return new ResponseEntity<>(studentService.swapClassroomOfStudents(oldClassroomId,newClassroomId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStudent(@PathVariable String id) throws StudentNotFoundException {
        studentService.deleteStudent(id);
    }


}
