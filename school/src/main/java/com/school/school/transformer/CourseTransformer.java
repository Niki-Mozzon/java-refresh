package com.school.school.transformer;

import com.school.school.data.dto.input.CourseInput;
import com.school.school.data.dto.output.CourseOutput;
import com.school.school.data.model.Course;
import com.school.school.data.model.Teacher;
import org.springframework.stereotype.Component;

@Component
public class CourseTransformer {


    public CourseOutput modelToOutput(Course model) {
        return CourseOutput.builder()
                .id(model.getId())
                .teacher( model.getTeacher()==null?null:model.getTeacher().getId())
                .name(model.getName())
                .build();
    }

    public Course inputToModel(CourseInput input) {
        return Course.builder()
                .teacher(Teacher.builder().id(input.getTeacher()).build())
                .name(input.getName())
                .build();
    }
}
