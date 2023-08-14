package com.school.school.service.impl;

import com.school.school.data.dto.input.ExamInput;
import com.school.school.data.dto.output.ExamOutput;
import com.school.school.data.model.Exam;
import com.school.school.data.model.key.ExamKey;
import com.school.school.exception.ExamNotFoundException;
import com.school.school.repository.ExamRepository;
import com.school.school.service.ExamService;
import com.school.school.transformer.ExamTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    private final ExamRepository examRepository;

    @Autowired
    private ExamTransformer transformer;

    @Override
    public ExamOutput getExamById(String idStudent, String idCourse) {
        Exam exam = examRepository.findById(new ExamKey(idCourse, idStudent)).orElse(null);
        if (exam == null) {
            throw new ExamNotFoundException(idStudent, idCourse);
        }
        return transformer.modelToOutput(exam);
    }

    @Override
    public List<ExamOutput> getAllExams() {
        return examRepository.findAll().stream().map(transformer::modelToOutput).toList();
    }

    @Override
    public List<ExamOutput> getAllExamsByStudent(String idStudent) {
        return examRepository.findAllExamsByStudent(idStudent).stream().map(transformer::modelToOutput).toList();
    }

    @Override
    public List<ExamOutput> getAllExamsByCourse(String idCourse) {
        return examRepository.findAllExamsByCourse(idCourse).stream().map(transformer::modelToOutput).toList();
    }

    @Override
    public ExamOutput addExam(ExamInput input) {
        Exam model = transformer.inputToModel(input);
        Exam modelOutput = examRepository.save(model);
        return transformer.modelToOutput(modelOutput);
    }

    @Override
    public Boolean deleteExam(String idStudent, String idCourse) {
        ExamKey examKey = new ExamKey(idCourse, idStudent);
        boolean exists = examRepository.existsById(examKey);
        if (exists) {
            examRepository.deleteById(examKey);
            return !examRepository.existsById(examKey);
        }
        throw new ExamNotFoundException(idStudent,idCourse);
    }
}
