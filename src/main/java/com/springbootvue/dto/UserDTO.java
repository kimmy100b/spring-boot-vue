package com.springbootvue.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor // 자동으로 모든 매변수를 받는 생성자를 생성
@Getter // Getter 생성
@Setter // Setter 생성
public class UserDTO {
    private String user_id;
    private String user_name;
}