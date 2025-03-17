package com.tkx.service;

import com.tkx.pojo.Student;
import com.tkx.pojo.StudentDTO;

import java.util.List;

/**
 * @Author tkx
 * @Date 2025 03 17 21 35
 **/
public interface StudentInterface {
    public List<Student> getList(StudentDTO dto);
}
