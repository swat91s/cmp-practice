package com.ford.crudops.students.Domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Student {
    @Id
    //@GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name= "ID", unique = true, nullable = false)
    private Long id;
    private String name;
    private String passportNumber;
}


