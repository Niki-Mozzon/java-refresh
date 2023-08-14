package com.school.school.utils;

import com.school.school.data.dto.input.ExamInput;
import com.school.school.data.dto.output.ExamOutput;
import com.school.school.data.model.Course;
import com.school.school.data.model.Exam;
import com.school.school.data.model.Student;
import com.school.school.data.model.key.ExamKey;

public class ExamGenerator {
    public static Exam generateExam(){
        return Exam.builder()
                .id(new ExamKey("course1","student1"))
                .course(Course.builder().id("course1").build())
                .student(Student.builder().id("student1").build())
                .rate(5.6)
                .build();
    }

    public static ExamOutput generateExamOutput(){
        return ExamOutput.builder()
                .course("course1")
                .student("student1")
                .rate(5.6)
                .build();
    }
    public static ExamInput generateExamInput(){
        return ExamInput.builder()
                .course("course1")
                .student("student1")
                .rate(5.6)
                .build();
    }
}
