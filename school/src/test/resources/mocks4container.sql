TRUNCATE TABLE public.students CASCADE;
TRUNCATE TABLE public.teachers CASCADE;
TRUNCATE TABLE public.courses CASCADE;
TRUNCATE TABLE public.exams CASCADE;

INSERT INTO public.students(id, classroom_id, name)	VALUES ('student1', 'classroom1', 'Pele');
INSERT INTO public.students(id, classroom_id, name)	VALUES ('student2', 'classroom2', 'Ibrahimovich');
INSERT INTO public.students(id, classroom_id, name)	VALUES ('student3', 'classroom2', 'Messi');

INSERT INTO public.teachers(id, name)VALUES ('teacher1', 'Ronaldo');
INSERT INTO public.teachers(id, name)VALUES ('teacher2', 'Cassano');
INSERT INTO public.teachers(id, name)VALUES ('teacher3', 'Maradona');

INSERT INTO public.courses(id, name, teacher_id)VALUES ('course1', 'Geography', 'teacher1');
INSERT INTO public.courses(id, name, teacher_id)VALUES ('course2', 'History', 'teacher1');
INSERT INTO public.courses(id, name, teacher_id)VALUES ('course3', 'Math', 'teacher2');

INSERT INTO public.exams(course_id, student_id, rate)VALUES ('course1', 'student1',5.6);
INSERT INTO public.exams(course_id, student_id, rate)VALUES ('course2', 'student1',7);
INSERT INTO public.exams(course_id, student_id, rate)VALUES ('course1', 'student2',9);
INSERT INTO public.exams(course_id, student_id, rate)VALUES ('course3', 'student2',8);
INSERT INTO public.exams(course_id, student_id, rate)VALUES ('course3', 'student3',8);
INSERT INTO public.exams(course_id, student_id, rate)VALUES ('course2', 'student3',3);



