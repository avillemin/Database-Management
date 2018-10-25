INSERT INTO student (student_id, name) VALUES ('2014CS15684', 'Soham ROY');
INSERT INTO student (student_id, name) VALUES ('2017EE19079', 'Quentin BOURGUE');
INSERT INTO student (student_id, name) VALUES ('2017EE19080', 'Antonin VILLEMIN');
INSERT INTO student (student_id, name) VALUES ('2017CS17001', 'Nikunj MURUGAVEL');
INSERT INTO student (student_id, name) VALUES ('2014CS14815', 'Gopinath VARNASI');

INSERT INTO course (course_id, name) VALUES ('ELL715', 'Digital Image Processing');
INSERT INTO course (course_id, name) VALUES ('ELL316', 'Introduction to VLSI Design');
INSERT INTO course (course_id, name) VALUES ('ELL201', 'Digital Electronics');
INSERT INTO course (course_id, name) VALUES ('COL362', 'Introduction to Database Mgmt. Syst.');

INSERT INTO teacher (teacher_id, name) VALUES ('2011TT85641', 'Monika AGGARWAL');
INSERT INTO teacher (teacher_id, name) VALUES ('2013TT77845', 'Abhisek DIXIT');
INSERT INTO teacher (teacher_id, name) VALUES ('2016TT96387', 'Anirban SEN');

INSERT INTO section (course_id, section_number) VALUES ('ELL715','A');
INSERT INTO section (course_id, section_number) VALUES ('ELL316','A');
INSERT INTO section (course_id, section_number) VALUES ('ELL201','B');
INSERT INTO section (course_id, section_number) VALUES ('COL362','C');

INSERT INTO teaches (course_id,teacher_id) VALUES ('ELL715','2013TT77845');
INSERT INTO teaches (course_id,teacher_id) VALUES ('ELL201','2013TT77845');
INSERT INTO teaches (course_id,teacher_id) VALUES ('COL362','2016TT96387');

INSERT INTO registers (student_id,course_id) VALUES ('2014CS15684','ELL715');
INSERT INTO registers (student_id,course_id) VALUES ('2017EE19079','COL362');
INSERT INTO registers (student_id,course_id) VALUES ('2017EE19079','ELL316');
INSERT INTO registers (student_id,course_id) VALUES ('2017EE19080','ELL201');
INSERT INTO registers (student_id,course_id) VALUES ('2017CS17001','ELL715');
INSERT INTO registers (student_id,course_id) VALUES ('2017CS17001','ELL201');


SELECT student.student_id FROM student
LEFT JOIN registers ON student.student_id = registers.student_id
WHERE SUBSTRING(registers.course_id, 1, 3) = 'ELL'
ORDER BY student.student_id ;

SELECT course FROM course
LEFT JOIN section ON course.course_id = section.course_id
WHERE section.section_number = 'A'
ORDER BY course.name ;

ALTER TABLE student
ADD birthday DATE ;

ALTER TABLE student
DROP COLUMN birthday ;

UPDATE course
SET course_id = 'COL632'
WHERE course_id = 'COL362' ;

UPDATE student
SET student_id = REPLACE(student_id, 'EE', 'EL') ;

SELECT COUNT(student.student_id)
FROM student
LEFT JOIN registers ON student.student_id = registers.student_id
WHERE SUBSTRING(registers.course_id, 1, 3) = 'COL' ;
