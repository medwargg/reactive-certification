package com.certification.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    private String id;
    private String name;
    private String lastName;
    private String socialSecurity;
    private Integer age;

}
