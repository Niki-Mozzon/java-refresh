package com.school.school.service;

import com.school.school.data.dto.input.StudentInput;
import com.school.school.data.dto.output.StudentOutput;
import com.school.school.exception.StudentNotFoundException;

import java.util.List;
import java.util.Map;

public interface StudentService {
    StudentOutput getStudent(String id) throws StudentNotFoundException;

    List<StudentOutput> getAllStudents();

    List<StudentOutput> getStudentsByClassroom(String id);

    StudentOutput addStudent(StudentInput dto) ;

    StudentOutput setClassroomOfStudent(String studentId, String classroomId) throws StudentNotFoundException;
    List<StudentOutput> setClassroomOfStudents(String classroomId, List<String> studentIds) throws StudentNotFoundException;

    void deleteStudent(String id) throws StudentNotFoundException;

    Map<String, String> getStudentNamesByClassroom(String id);

    List<StudentOutput> swapClassroomOfStudents(String oldClassroomId, String newClassroomId);

    boolean classroomHasStudents(String id);
}
