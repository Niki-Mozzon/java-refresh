package com.school.school.transformer;

import com.school.school.data.dto.input.StudentInput;
import com.school.school.data.dto.output.StudentOutput;
import com.school.school.data.model.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentTransformer {

    public StudentOutput modelToOutput(Student model) {
        return StudentOutput.builder()
                .id(model.getId())
                .name(model.getName())
                .classroomId(model.getClassroomId())
                .build();
    }

    public Student inputToModel(StudentInput input) {
        return Student.builder()
                .name(input.getName())
                .classroomId(input.getClassroomId())
                .build();
    }
}
