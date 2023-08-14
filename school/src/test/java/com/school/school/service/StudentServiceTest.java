package com.school.school.service;

import com.school.school.data.dto.input.StudentInput;
import com.school.school.data.dto.output.StudentOutput;
import com.school.school.data.dto.projection.StudentMiniProjection;
import com.school.school.exception.ClassroomNotFoundException;
import com.school.school.exception.StudentNotFoundException;
import com.school.school.data.model.Student;
import com.school.school.repository.StudentRepository;
import com.school.school.utils.StudentGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class StudentServiceTest {

    @Autowired
    private StudentService sut;
    @MockBean
    private StudentRepository repo;


    @Test
    void getStudent() throws StudentNotFoundException {
        String id = "student1";
        when(repo.findById(id)).thenReturn(Optional.ofNullable(StudentGenerator.generateStudent()));
        assertEquals(StudentGenerator.generateStudentOutput(), sut.getStudent(id));
    }

    @Test
    void getStudent_notFound() throws StudentNotFoundException {
        String id = "0";
        when(repo.findById(id)).thenReturn(Optional.empty());
        assertThrows(StudentNotFoundException.class, () -> sut.getStudent(id));
    }

    @Test
    void getAllStudents() {
        when(repo.findAll()).thenReturn(Optional.of(List.of(StudentGenerator.generateStudent(), StudentGenerator.generateStudent())).orElse(null));
        assertEquals(2, sut.getAllStudents().size());
    }

    @Test
    void getStudentsByClassroom() {
        when(repo.findByClassroomId("classroom1")).thenReturn(Optional.of(List.of(StudentGenerator.generateStudent(), StudentGenerator.generateStudent())).orElse(null));
        assertEquals(2, sut.getStudentsByClassroom("classroom1").size());
    }

    @Test
    void getStudentNamesByClassroom() {
        Student mock = StudentGenerator.generateStudent();
        when(repo.getStudentNamesByClassroom("classroom1")).thenReturn(List.of(new StudentMiniProjection() {
            @Override
            public String getId() {
                return mock.getId();
            }

            @Override
            public String getName() {
                return mock.getName();
            }
        }));
        Map<String, String> res = sut.getStudentNamesByClassroom("classroom1");
        assertEquals(1, res.size());
        assertEquals(mock.getName(), res.get(mock.getId()));
    }

    @Test
    void addStudent()  {
        StudentInput dto = StudentGenerator.generateStudentInput();
        Student model = StudentGenerator.generateStudent();
        model.setId(null);
        when(repo.save(model)).thenReturn(StudentGenerator.generateStudent());
        assertEquals(StudentGenerator.generateStudentOutput().getName(), sut.addStudent(dto).getName());
        assertEquals(StudentGenerator.generateStudentOutput().getClassroomId(), sut.addStudent(dto).getClassroomId());
    }

    @Test
    void setClassroomOfStudent() throws StudentNotFoundException {
        StudentOutput output = StudentGenerator.generateStudentOutput();
        output.setClassroomId("classroom2");
        when(repo.existsById(output.getId())).thenReturn(true);
        when(repo.setStudentClassroom(output.getId(), output.getClassroomId())).thenReturn(Student.builder().id(output.getId()).name( output.getName()).classroomId(output.getClassroomId()).build());
        assertEquals(output, sut.setClassroomOfStudent(output.getId(), output.getClassroomId()));
    }

    @Test
    void setClassroomOfStudent_notFound() {
        StudentInput input = StudentGenerator.generateStudentInput();
        when(repo.existsById("student1")).thenReturn(false);
        assertThrows(StudentNotFoundException.class, () -> sut.setClassroomOfStudent("student1", input.getClassroomId()));
    }

    @Test
    void setClassroomOfStudents() throws StudentNotFoundException {
        StudentOutput output = StudentGenerator.generateStudentOutput();
        StudentOutput output2 = StudentGenerator.generateStudentOutput();
        output.setClassroomId("classroom2");
        output2.setClassroomId("classroom2");
        output2.setId("student2");
        when(repo.existsById(any())).thenReturn(true);
        when(repo.setStudentsClassroom("classroom3", List.of("student1", "student2"))).thenReturn(List.of(
                Student.builder().id(output.getId()).name( output.getName()).classroomId(output.getClassroomId()).build(),
                Student.builder().id(output2.getId()).name( output2.getName()).classroomId(output2.getClassroomId()).build()));
        List<StudentOutput> studentsOutput = sut.setClassroomOfStudents("classroom3", List.of("student1", "student2"));
        assertAll(
                () -> {
                    assertEquals(2, studentsOutput.size());
                    assertTrue(studentsOutput.stream().allMatch(x -> x.getName().equals(output.getName())));
                }
        );
    }


    @Test
    void deleteStudent_NotFound() {
        String id = "0";
        when(repo.existsById(id)).thenReturn(false);
        assertThrows(StudentNotFoundException.class, () -> sut.deleteStudent(id));
    }


    @Test
    void swapClassroomOfStudents() {
        String oldClass = "classroom1";
        String newClass = "classroom3";
        StudentOutput output = StudentGenerator.generateStudentOutput();
        StudentOutput output2 = StudentGenerator.generateStudentOutput();
        output2.setId("student2");
        when(repo.classroomHasStudents(any())).thenReturn(true);
        when(repo.swapClassroomOfStudents(oldClass, newClass)).thenReturn(List.of(
                Student.builder().id(output.getId()).name( output.getName()).classroomId(newClass).build(),
                Student.builder().id(output2.getId()).name( output2.getName()).classroomId(newClass).build()));
        List<StudentOutput> studentsOutput = sut.swapClassroomOfStudents(oldClass, newClass);
        assertAll(
                () -> {
                    assertEquals(2, studentsOutput.size());
                    assertTrue(studentsOutput.stream().allMatch(x -> x.getClassroomId().equals("classroom3")));
                }
        );
    }

    @Test
    void swapClassroomOfStudents_notFound() {
        when(repo.classroomHasStudents(any())).thenReturn(false);
        assertThrows(ClassroomNotFoundException.class,()-> sut.swapClassroomOfStudents("student1", "000"));
    }
}