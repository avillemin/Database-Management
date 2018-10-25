CREATE TABLE student(
    student_id TEXT PRIMARY KEY,
    name TEXT
);

CREATE TABLE teacher (
    teacher_id TEXT PRIMARY KEY,
    name TEXT
    
);

CREATE TABLE course (
    course_id TEXT PRIMARY KEY,
    name TEXT
);

CREATE TABLE registers
(
    student_id TEXT NOT NULL,
    course_id TEXT NOT NULL,
    CONSTRAINT PK_registers PRIMARY KEY
    (
        student_id,
        course_id
    ),
CONSTRAINT student_registers
FOREIGN KEY (student_id) REFERENCES student (student_id)
ON DELETE CASCADE
ON UPDATE CASCADE, 
CONSTRAINT course_registers
FOREIGN KEY (course_id) REFERENCES course (course_id)
ON DELETE CASCADE
ON UPDATE CASCADE
);


CREATE TABLE teaches
(
    teacher_id TEXT NOT NULL,
    course_id TEXT NOT NULL,
    CONSTRAINT PK_teaches PRIMARY KEY
    (
        teacher_id,
        course_id
    ),
    CONSTRAINT teacher_teaches
    FOREIGN KEY (teacher_id) REFERENCES teacher (teacher_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    CONSTRAINT course_teaches
    FOREIGN KEY (course_id) REFERENCES course (course_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE section
(
    section_number CHAR,
    course_id TEXT NOT NULL,
    
   CONSTRAINT FK_section_number FOREIGN KEY (course_id)
   REFERENCES course(course_id)
   ON DELETE CASCADE
   ON UPDATE CASCADE,
   
   CONSTRAINT chk_section_number CHECK (section_number IN ('A', 'B', 'C', 'D')),

   CONSTRAINT PK_section PRIMARY KEY
    (
        section_number,
        course_id
    )
);
