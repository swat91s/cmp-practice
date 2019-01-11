package com.ford.crudops.students.Api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ford.crudops.students.Domain.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentResponseJson {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String responseStatus;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private StudentJson student;

}
