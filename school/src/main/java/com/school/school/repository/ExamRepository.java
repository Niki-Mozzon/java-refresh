package com.school.school.repository;

import com.school.school.data.model.Exam;
import com.school.school.data.model.key.ExamKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, ExamKey> {
    @Query(value="SELECT * FROM exams e WHERE e.course_id=?1",nativeQuery = true)
    List<Exam> findAllExamsByCourse(String idCourse);
    @Query(value="SELECT * FROM exams e WHERE e.student_id=?1",nativeQuery = true)
    List<Exam> findAllExamsByStudent(String idStudent);
}
