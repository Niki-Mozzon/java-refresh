package com.school.school.data.model;

import com.school.school.data.model.key.ExamKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name="exams")
@Entity
public class Exam {

    @EmbeddedId
    private ExamKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("student")
    @JoinColumn
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("course")
    @JoinColumn
    private Course course;

    private Double rate;
}
