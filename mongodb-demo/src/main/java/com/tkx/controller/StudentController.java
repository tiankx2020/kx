package com.tkx.controller;

import com.tkx.entity.Student;
import com.tkx.service.StudentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Random;
import java.util.UUID;

/**
 * @Author tkx
 * @Date 2025 01 05 15 34
 **/
@RestController
@RequestMapping("/student")
public class StudentController {


    @Resource
    private StudentService studentService;


    @PostMapping("/save")
    public void save(){
        for (int i = 0; i < 10; i++) {
            Student student = new Student();
            student.setName(UUID.randomUUID().toString());
            student.setAge(new Random().nextInt(100));
            studentService.save(student);
        }
    }

    /**
     * 字段都一致的时候，才能够删除
     * @param student
     */
    @PostMapping("/delete")
    public void delete(@RequestBody Student student){
        studentService.delete(student);
    }

    @PostMapping("/getById")
    public Student getById(@RequestParam("id") String id){
        Student student = studentService.getById(id);
        return student;
    }

    @PostMapping("/updateById")
    public Student updateById(@RequestBody Student student ){
        return studentService.updateById(student);
    }
}
