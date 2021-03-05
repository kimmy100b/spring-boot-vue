package com.springbootvue.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
public class UserDTO {
    /** 사용자 sid - PK **/
    private int uid;

    /** 사용자 아이디 **/
    private String id;

    /** 사용자 비밀번호 **/
    private String pwd;

    /** 사용자 이름 **/
    private String name;

    /** 사용자 생년월일 **/
    private Date birthDate;

    /** 사용자 성별 **/
    private String gender;

    /** 사용자 전화번호 **/
    private String tel;

    /** 사용자 이메일 **/
    private String email;

    /** 사용자 프로필 URL **/
    private String imgUrl;

    /** 회원가입일 **/
    private Timestamp regDate;

    /** 회원수정일 **/
    private Timestamp modDate;

    /** 회원 권한(역할) **/
    private String roleName;
}
