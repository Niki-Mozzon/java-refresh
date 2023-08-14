package com.school.school.transformer;

import com.school.school.data.dto.input.TeacherInput;
import com.school.school.data.dto.output.TeacherOutput;
import com.school.school.data.model.Teacher;
import com.school.school.data.model.Teacher;
import org.springframework.stereotype.Component;

@Component
public class TeacherTransformer {
    public TeacherOutput modelToOutput(Teacher model) {
        return TeacherOutput.builder()
                .id(model.getId())
                .name(model.getName())
                .build();
    }

    public Teacher inputToModel(TeacherInput input) {
        return Teacher.builder()
                .name(input.getName())
                .build();
    }
}
