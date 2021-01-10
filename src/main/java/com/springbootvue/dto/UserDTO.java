package com.springbootvue.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter // Getter 생성
@Setter // Setter 생성
public class UserDTO {
    private String user_id;
    private String user_name;
}