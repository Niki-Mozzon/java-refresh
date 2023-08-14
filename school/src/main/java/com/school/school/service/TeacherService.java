package com.school.school.service;

import com.school.school.data.dto.input.TeacherInput;
import com.school.school.data.dto.output.TeacherOutput;

import java.util.List;

public interface TeacherService {
TeacherOutput getTeacherById(String id);
List<TeacherOutput> getAllTeachers();
TeacherOutput addTeacher(TeacherInput input);
boolean deleteTeacher(String id);
}
