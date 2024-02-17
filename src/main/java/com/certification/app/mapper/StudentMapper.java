package com.certification.app.mapper;

import com.certification.app.document.Student;
import com.certification.app.dto.StudentDTO;
import org.mapstruct.Mapper;

@Mapper
public interface StudentMapper {

    Student toDocument(StudentDTO studentDTO);

    StudentDTO toDto(Student student);

}
