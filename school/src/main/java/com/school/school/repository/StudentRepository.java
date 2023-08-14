package com.school.school.repository;

import com.school.school.data.dto.projection.StudentMiniProjection;
import com.school.school.data.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,String> {
     List<Student> findByClassroomId(String id);
     @Query(value="SELECT s.id,s.name FROM students s WHERE s.classroom_Id=?1",nativeQuery = true)
     List<StudentMiniProjection> getStudentNamesByClassroom(String id);
     @Query(value = "UPDATE students SET classroom_Id=?2 WHERE id=?1 RETURNING *",nativeQuery = true)
     Student setStudentClassroom(String studentId, String classroomId);

     @Query(value = "UPDATE students SET classroom_Id=?1 WHERE id in ?2 RETURNING *",nativeQuery = true)
     List<Student> setStudentsClassroom(String classroomId, List<String> studentIds);

     @Query(value = "UPDATE students SET classroom_Id=?2 WHERE classroom_Id =?1 RETURNING *",nativeQuery = true)
     List<Student> swapClassroomOfStudents(String oldClassroomId, String newClassroomId);

     @Query(value = "SELECT CASE WHEN EXISTS " +
             "(SELECT * FROM Students " +
             "WHERE classroom_Id=?1)"+
             "THEN CAST(1 AS BIT)" +
             "ELSE CAST(0 AS BIT) END "
             ,nativeQuery = true)
     boolean classroomHasStudents(String classroomId);
}
