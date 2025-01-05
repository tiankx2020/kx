package com.tkx.dao;

import com.tkx.entity.Comment;
import com.tkx.entity.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author tkx
 * @Date 2025 01 05 15 33
 **/
public interface StudentRepository extends MongoRepository<Student,String> {
}
