package com.ford.crudops.students.util;

import com.ford.crudops.students.Api.StudentJson;
import com.ford.crudops.students.Domain.Student;

public class TransformerUtil {
    public static StudentJson transformStudentData (Student student){
        return StudentJson.builder().id(student.getId())
                .name(student.getName())
                .passportNumber(student.getPassportNumber())
                .build();
    }
}
