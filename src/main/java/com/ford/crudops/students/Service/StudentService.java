package com.ford.crudops.students.Service;

import com.ford.crudops.students.Domain.Student;
import com.ford.crudops.students.Repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class StudentService {

    private StudentRepository studRepo;

    public StudentService(StudentRepository studRepo) {
        this.studRepo = studRepo;
    }

    public List<Student> getAllStudentDetails() {
        return studRepo.findAll();
    }

    public Student getStudentById(long id) {
        Optional<Student> student = studRepo.findById(id);
        Student studentData;
        try {
            studentData = student.get();
        } catch (NoSuchElementException e) {
            studentData = null;
        }
        return studentData;
    }
//    public Student createStudent(Student student) {
//        Student savedStudent = studRepo.save(student);
//        return savedStudent;
//    }

    public Student createStudent(Student student) {
        long studId = student.getId();
        if(getStudentById(studId)==null) {
            Student savedStudent = studRepo.save(student);
            return savedStudent;
        } else {
            return null;
        }

    }

    public Student updateStudent(Student student, long id) {
        Optional<Student> studentt = studRepo.findById(id);
        if (!studentt.isPresent())
            return null;
        student.setId(id);
        Student updatedStudent = studRepo.save(student);
        return updatedStudent;
    }

    public void deleteStudent(long id) {
        studRepo.deleteById(id);
    }

}
