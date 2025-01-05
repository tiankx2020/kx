package com.tkx.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @Author tkx
 * @Date 2025 01 05 15 32
 **/
@Data
public class Student {
    @Id
    private String id;
    private String name;
    private Integer age;
}
