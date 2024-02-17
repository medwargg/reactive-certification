package com.certification.app.repository;

import com.certification.app.document.Student;
import com.certification.app.repository.generic.CRUDRepository;

// The @Repository annotation is optional when the interface extends from a repository defined by Spring.
public interface StudentRepository extends CRUDRepository<Student, String> {
}
