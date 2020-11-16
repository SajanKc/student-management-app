package com.kcsajan.main.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kcsajan.main.model.Student;
import com.kcsajan.main.repository.StudentRepository;

import net.bytebuddy.implementation.FieldAccessor.FieldNameExtractor;

@RestController
@RequestMapping("/api")
public class StudentController {

	@Autowired
	StudentRepository studentRepository;

	@GetMapping("/students")
	public ResponseEntity<List<Student>> getStudents() {
		try {
			List<Student> students = studentRepository.findAll();
			if (students.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(students, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/students/{id}")
	public ResponseEntity<Student> getStudent(@PathVariable("id") Integer id) {
		try {
			Optional<Student> student = studentRepository.findById(id);
			if (student.isPresent())
				return new ResponseEntity<>(student.get(), HttpStatus.OK);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("students")
	public ResponseEntity<Student> addStudent(@RequestBody Student student) {
		try {
			Student studentData = studentRepository.save(student);
			return new ResponseEntity<>(studentData, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/students/{id}")
	public ResponseEntity<Student> updateStudents(@PathVariable("id") Integer id, @RequestBody Student student) {
		try {
			Optional<Student> studentData = studentRepository.findById(id);
			if (studentData.isPresent()) {
				Student updatedStudent = studentData.get();
				updatedStudent.setRollNo(student.getRollNo());
				updatedStudent.setAddress(student.getAddress());
				updatedStudent.setEmail(student.getEmail());
				updatedStudent.setFname(student.getFname());
				updatedStudent.setLname(student.getLname());
				updatedStudent.setPhone(student.getPhone());
				return new ResponseEntity<>(studentRepository.save(updatedStudent), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/students/{id}")
	public ResponseEntity<HttpStatus> deleteStudent(@PathVariable("id") Integer id) {
		try {
			studentRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/students")
	public ResponseEntity<HttpStatus> deleteStudents() {
		try {
			studentRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
