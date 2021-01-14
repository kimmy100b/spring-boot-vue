package com.springbootvue.controller;

import com.springbootvue.Service.UserService;
import com.springbootvue.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 사용자 정보 저장(회원가입)
    @PostMapping("/saveUserInfo")
    public void saveUserInfo(@RequestBody UserDTO userDTO){
        
        userService.saveUserInfo(userDTO);
    }
}
