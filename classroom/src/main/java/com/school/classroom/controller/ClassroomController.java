package com.school.classroom.controller;

import com.school.classroom.dto.ClassroomInputDto;
import com.school.classroom.dto.ClassroomOutputDto;
import com.school.classroom.exception.ClassroomNotFoundException;
import com.school.classroom.service.ClassroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/classrooms")
@RequiredArgsConstructor
public class ClassroomController {
    @Autowired
    private final ClassroomService classroomService;

    @GetMapping("/{id}")
    public ResponseEntity<ClassroomOutputDto> getClassroomById(@PathVariable String id) throws ClassroomNotFoundException {
        return new ResponseEntity<>(classroomService.getClassroomById(id), HttpStatus.FOUND);

    }

    @GetMapping
    public ResponseEntity<List<ClassroomOutputDto>> getAllClassrooms() {
        return new ResponseEntity<>(classroomService.getAllClassrooms(), HttpStatus.OK);
    }

    @GetMapping("/query={infix}")
    public ResponseEntity<List<ClassroomOutputDto>> findByNameContaining(@PathVariable String infix) {
        return new ResponseEntity<>(classroomService.findByNameContaining(infix), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ClassroomOutputDto> addClassroom(@RequestBody ClassroomInputDto input) {
        return new ResponseEntity<>(classroomService.addClassroom(input), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteClassroomById(@PathVariable String id) throws ClassroomNotFoundException, URISyntaxException, IOException, InterruptedException {
        classroomService.deleteClassroomById(id);
    }
}
