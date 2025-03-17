package com.tkx.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author tkx
 * @Date 2025 03 17 21 05
 **/
@Data
public class Student implements Serializable {
    private int id;
    private String username;
    private Integer age;
}
