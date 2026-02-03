package com.example.schoolmanager.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.schoolmanager.model.Student;
import com.example.schoolmanager.repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    public Student save(Student s) { return repo.save(s); }

    public List<Student> getAll() { return repo.findAll(); }

    public Optional<Student> getById(int id) { return repo.findById(id); }

    public boolean delete(int id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }

    // SMART SEARCH TOÀN DIỆN
    public List<Student> smartSearch(String keyword) {

        if (keyword == null || keyword.trim().isEmpty())
            return repo.findAll();

        String key = keyword.toLowerCase().trim();

        return repo.findAll().stream().filter(s -> {

            // ID
            if (isNumber(key) && s.getId() != null && s.getId().toString().equals(key))
                return true;

            // TUỔI
            if (isNumber(key) && s.getBirthDate() != null) {
                int age = Period.between(s.getBirthDate(), LocalDate.now()).getYears();
                if (String.valueOf(age).equals(key)) return true;
            }

            // NGÀY SINH yyyy-mm-dd
            if (isDate(key) && s.getBirthDate() != null &&
                s.getBirthDate().equals(LocalDate.parse(key)))
                return true;

            // TÊN
            if (s.getName() != null && s.getName().toLowerCase().contains(key))
                return true;

            // ĐỊA CHỈ
            if (s.getAddress() != null && s.getAddress().toLowerCase().contains(key))
                return true;

            // EMAIL
            if (s.getEmail() != null && s.getEmail().toLowerCase().contains(key))
                return true;

            // LỚP
            if (s.getStudentClass() != null && s.getStudentClass().toLowerCase().contains(key))
                return true;

            return false;

        }).collect(Collectors.toList());
    }

    private boolean isNumber(String s) {
        try { Integer.parseInt(s); return true; }
        catch(Exception e){ return false; }
    }

    private boolean isDate(String s) {
        try { LocalDate.parse(s); return true; }
        catch(Exception e){ return false; }
    }
}
