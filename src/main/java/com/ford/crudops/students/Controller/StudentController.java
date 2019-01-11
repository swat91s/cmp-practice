package com.ford.crudops.students.Controller;

import com.ford.cloudnative.base.api.BaseBodyError;
import com.ford.cloudnative.base.api.BaseBodyResponse;
import com.ford.crudops.students.Api.StudentResponseJson;
import com.ford.crudops.students.Domain.Student;
import com.ford.crudops.students.Service.StudentService;

import com.ford.crudops.students.util.TransformerUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping("api/students")
    @CrossOrigin("*")
    public List<Student> getStudentDetails() {
        System.out.println("Print this ***");
        return studentService.getAllStudentDetails();
    }

    @GetMapping("api/students/{id}")
    @CrossOrigin("*")
    public ResponseEntity<BaseBodyResponse<StudentResponseJson>> getStudentById(@PathVariable long id) {
        Student stud = studentService.getStudentById(id);
        if (stud == null){
            return new ResponseEntity<>(BaseBodyResponse.error(BaseBodyError.builder()
            .errorCode(String.valueOf(HttpStatus.BAD_REQUEST))
                    .message("No Matching records found")
            .attribute("Status context","Student")
            .build()),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(BaseBodyResponse.result(StudentResponseJson.builder()
        .student(TransformerUtil.transformStudentData(stud))
                .responseStatus("Success")
        .build()), HttpStatus.OK);
    }

    @PostMapping("api/students")
    @CrossOrigin("*")
    public ResponseEntity createStudent(@Valid @RequestBody Student student) {

        try{
            if(studentService.createStudent(student)==null){
                throw new DataIntegrityViolationException("Record already exists");
            }
        } catch (Exception e){
            if(e instanceof DataIntegrityViolationException){
                return new ResponseEntity<>(BaseBodyResponse.error(BaseBodyError.builder()
                .errorCode(String.valueOf(HttpStatus.CONFLICT))
                .message("Student ID is already in the database.")
                .attribute("statusContext","Student")
                .build()),HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<>(BaseBodyResponse.error(BaseBodyError.builder()
                        .errorCode(String.valueOf(HttpStatus.BAD_REQUEST))
                        .message("Some internal server error has occurred.")
                        .attribute("statusContext","Student")
                        .build()),HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(BaseBodyResponse.result(StudentResponseJson.builder().responseStatus("Student record successfully created.").build()),HttpStatus.OK);
    }

    @PutMapping("api/students/{id}")
    @CrossOrigin("*")
    public ResponseEntity<Object> updateStudent(@Valid @RequestBody Student student, @PathVariable long id) {
        Student updateStatus = studentService.updateStudent(student, id);
        if (updateStatus == null)
            return new ResponseEntity<>(BaseBodyResponse.result(StudentResponseJson.builder().
                    responseStatus("Student record to be updated not found in Database.").build()),HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(BaseBodyResponse.result(StudentResponseJson.builder().responseStatus("Student record successfully updated.").build()),HttpStatus.OK);
    }

    @DeleteMapping("api/students/{id}")
    @CrossOrigin("*")
    public void deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
    }
}
