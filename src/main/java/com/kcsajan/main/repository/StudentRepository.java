package com.kcsajan.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kcsajan.main.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
