package com.school.classroom.transformer;

import com.school.classroom.dto.ClassroomInputDto;
import com.school.classroom.dto.ClassroomOutputDto;
import com.school.classroom.model.Classroom;
import org.springframework.stereotype.Component;

@Component
public class ClassroomTransformer {

    public ClassroomOutputDto modelToOutput(Classroom model){
        return ClassroomOutputDto.builder()
                .id(model.getId())
                .name(model.getName())
                .build();
    }

    public Classroom inputToModel(ClassroomInputDto dto) {
        return Classroom.builder()
                .name(dto.getName())
                .build();
    }
}
