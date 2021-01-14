package com.springbootvue.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.sql.Timestamp;

@ToString
@Getter // Getter 생성
@Setter // Setter 생성
public class UserDTO {
    //user table
    private int uid;
    private String id;
    private String pwd;
    private String name;
    private Date birthDate;
    private String gender;
    private String tel;
    private String email;
    private Timestamp regDate;
    private Timestamp modDate;

    // role table
    private String roleName;
}
