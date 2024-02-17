package com.certification.app.service.impl;

import com.certification.app.document.Student;
import com.certification.app.repository.StudentRepository;
import com.certification.app.repository.generic.CRUDRepository;
import com.certification.app.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl extends CRUDServiceImpl<Student, String> implements StudentService {

    private final StudentRepository studentRepository;

    protected CRUDRepository<Student, String> getMainRepository() {
        return studentRepository;
    }

}
