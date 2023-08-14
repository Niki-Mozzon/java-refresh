package com.school.classroom.repository;

import com.school.classroom.model.Classroom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomRepository extends MongoRepository<Classroom, String> {
    public List<Classroom> findByNameContaining(String infix);
}
