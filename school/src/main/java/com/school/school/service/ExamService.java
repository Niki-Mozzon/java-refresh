package com.school.school.service;

import com.school.school.data.dto.input.ExamInput;
import com.school.school.data.dto.output.ExamOutput;

import java.util.List;

public interface ExamService {
    ExamOutput getExamById(String idStudent,String idCourse);
    List<ExamOutput> getAllExams();
    List<ExamOutput> getAllExamsByStudent(String idStudent);
    List<ExamOutput> getAllExamsByCourse(String idCourse);
    ExamOutput addExam(ExamInput input);

    Boolean deleteExam(String idStudent, String idCourse);
}
