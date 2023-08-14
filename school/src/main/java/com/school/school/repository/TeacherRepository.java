package com.school.school.repository;

import com.school.school.data.model.Teacher;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE courses SET teacher_id=NULL WHERE teacher_id=?1 ",nativeQuery = true)
    void removeTeacherFromCourses( String id);

}
