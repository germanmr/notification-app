package com.acme.notificationapp.redis.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    private final StudentRepository studentRepository;

    @Autowired
    public RedisService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void newStudent() {
        Student student = new Student("Eng2015001", "John Doe", Student.Gender.MALE, 1);
        studentRepository.save(student);
    }
}