package com.school.school.transformer;

import com.school.school.data.dto.input.ExamInput;
import com.school.school.data.dto.output.ExamOutput;
import com.school.school.data.model.Course;
import com.school.school.data.model.Exam;
import com.school.school.data.model.Student;
import com.school.school.data.model.Teacher;
import com.school.school.data.model.key.ExamKey;
import org.springframework.stereotype.Component;

    @Component
public class ExamTransformer {
        public ExamOutput modelToOutput(Exam model) {
            return ExamOutput.builder()
                    .course(model.getId().getCourse())
                    .student(model.getId().getStudent())
                    .rate(model.getRate())
                    .build();
        }

        public Exam inputToModel(ExamInput input) {
            return Exam.builder()
                    .id(new ExamKey(input.getCourse(),input.getStudent()))
                    .course(Course.builder().id(input.getCourse()).build())
                    .student(Student.builder().id(input.getStudent()).build())
                    .rate(input.getRate())
                    .build();
        }
}
