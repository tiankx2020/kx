package com.tkx.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author tkx
 * @Date 2024 12 08 10 20
 **/
@Data
public class User implements Serializable {

    private Integer id;
    private String username;
    private String sex;

    private Date birthday;
    private String address;

    // public User() {
    // }
    //
    // public User(Integer id, String username, String sex, Date birthday, String address) {
    //     this.id = id;
    //     this.username = username;
    //     this.sex = sex;
    //     this.birthday = birthday;
    //     this.address = address;
    // }
    //
    // public Integer getId() {
    //     return id;
    // }
    //
    // public void setId(Integer id) {
    //     this.id = id;
    // }
    //
    // public String getUsername() {
    //     return username;
    // }
    //
    // public void setUsername(String username) {
    //     this.username = username;
    // }
    //
    // public String getSex() {
    //     return sex;
    // }
    //
    // public void setSex(String sex) {
    //     this.sex = sex;
    // }
    //
    // public Date getBirthday() {
    //     return birthday;
    // }
    //
    // public void setBirthday(Date birthday) {
    //     this.birthday = birthday;
    // }
    //
    // public String getAddress() {
    //     return address;
    // }
    //
    // public void setAddress(String address) {
    //     this.address = address;
    // }
}
