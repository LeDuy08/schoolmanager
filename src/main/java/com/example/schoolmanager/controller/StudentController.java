package com.example.schoolmanager.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.schoolmanager.model.Student;
import com.example.schoolmanager.service.StudentService;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Student> getAll() { return service.getAll(); }

    @PostMapping
    public Student add(@RequestBody Student s) { return service.save(s); }

    @PutMapping("/{id}")
    public ResponseEntity<Student> update(@PathVariable int id, @RequestBody Student s) {
        Optional<Student> opt = service.getById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        Student st = opt.get();
        st.setName(s.getName());
        st.setEmail(s.getEmail());
        st.setBirthDate(s.getBirthDate());
        st.setAddress(s.getAddress());
        st.setStudentClass(s.getStudentClass());
        return ResponseEntity.ok(service.save(st));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (!service.delete(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    // 🔍 SEARCH
    @GetMapping("/search")
    public List<Student> search(@RequestParam String keyword) {
        return service.smartSearch(keyword);
    }

}
