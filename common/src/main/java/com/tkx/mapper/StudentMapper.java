package com.tkx.mapper;

import com.tkx.pojo.Student;
import com.tkx.pojo.StudentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author tkx
 * @Date 2025 03 17 21 08
 **/
@Mapper
public interface StudentMapper {

    public List<Student> getStudentList(StudentDTO dto);

    public void save(Student student);
}
