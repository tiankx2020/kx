package com.tkx.service;

import com.tkx.dao.CommentRepository;
import com.tkx.dao.StudentRepository;
import com.tkx.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author tkx
 * @Date 2025 01 05 15 35
 **/
@Service
public class StudentService {

    @Resource
    private StudentRepository studentRepository;

    public void save(Student student){
        studentRepository.save(student);
    }

    public void delete(Student student){
        studentRepository.delete(student);
    }

    public Student getById(String id) {
        return studentRepository.findById(id).get();
    }

    public Student updateById(Student student){
        Student result = studentRepository.save(student);
        return result;
    }
}
