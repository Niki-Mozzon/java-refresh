package com.school.school.service.impl;

import com.school.school.data.dto.input.StudentInput;
import com.school.school.data.dto.output.StudentOutput;
import com.school.school.exception.ClassroomNotFoundException;
import com.school.school.exception.StudentNotFoundException;
import com.school.school.data.model.Student;
import com.school.school.repository.StudentRepository;
import com.school.school.service.StudentService;
import com.school.school.transformer.StudentTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentTransformer studentTransformer;

    @Override
    public StudentOutput getStudent(String id) throws StudentNotFoundException {
        Student res = studentRepository.findById(id).orElse(null);
        if (res == null) {
            throw new StudentNotFoundException(id);
        }
        return studentTransformer.modelToOutput(res);
    }

    @Override
    public List<StudentOutput> getAllStudents() {
        return studentRepository.findAll().stream().map(studentTransformer::modelToOutput).toList();
    }

    @Override
    public List<StudentOutput> getStudentsByClassroom(String id) {
        return studentRepository.findByClassroomId(id).stream().map(studentTransformer::modelToOutput).toList();
    }

    @Override
    public Map<String, String> getStudentNamesByClassroom(String id) {
        Map<String, String> map = new HashMap<>();
        studentRepository.getStudentNamesByClassroom(id).forEach(x ->
                map.put(x.getId(), x.getName())
        );
        return map;
    }

    @Override
    public List<StudentOutput> swapClassroomOfStudents(String oldClassroomId, String newClassroomId) {
        boolean oldExists = studentRepository.classroomHasStudents(oldClassroomId);
        if (oldExists) {
            List<Student> students = studentRepository.swapClassroomOfStudents(oldClassroomId, newClassroomId);
            return students.stream().map(studentTransformer::modelToOutput).toList();
        } else {
            throw new ClassroomNotFoundException(oldClassroomId);
        }
    }

    @Override
    public boolean classroomHasStudents(String id) {
        return studentRepository.classroomHasStudents(id);
    }


    @Override
    public StudentOutput addStudent(StudentInput dto)  {
        Student res = studentRepository.save(studentTransformer.inputToModel(dto));
        return studentTransformer.modelToOutput(res);
    }

    @Override
    public StudentOutput setClassroomOfStudent(String studentId, String classroomId) throws StudentNotFoundException {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            throw new StudentNotFoundException(studentId);
        }
        Student res = studentRepository.setStudentClassroom(studentId, classroomId);
        return studentTransformer.modelToOutput(res);
    }

    @Override
    public List<StudentOutput> setClassroomOfStudents(String classroomId, List<String> studentIds) throws StudentNotFoundException {
        studentIds.forEach(id -> {
            if (!studentRepository.existsById(id))
                throw new StudentNotFoundException(id);
        });
        List<Student> res = studentRepository.setStudentsClassroom(classroomId, studentIds);
        return res.stream().map(studentTransformer::modelToOutput).toList();
    }

    @Override
    public void deleteStudent(String id) throws StudentNotFoundException {
        boolean exists = studentRepository.existsById(id);
        if (!exists) {
            throw new StudentNotFoundException(id);
        }
        //ToDo: Check if there are exams linked to that student, if so delete them as well
        studentRepository.deleteById(id);
    }


}
