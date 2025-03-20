package com.tkx.service;

import com.tkx.mapper.StudentMapper;
import com.tkx.pojo.Student;
import com.tkx.pojo.StudentDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author tkx
 * @Date 2025 03 17 21 31
 **/
@Service
public class StudentImpl implements StudentInterface{

    @Resource
    private StudentMapper studentMapper;

    @Override
    public List<Student> getList(StudentDTO dto) {
        return studentMapper.getStudentList(dto);
    }

    @Override
    public void save(Student student) {
        studentMapper.save(student);
    }
}
