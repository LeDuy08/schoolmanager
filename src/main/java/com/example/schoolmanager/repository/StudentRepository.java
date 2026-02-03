package com.example.schoolmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.schoolmanager.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}
