package com.springbootvue.controller;

import com.springbootvue.Service.UserService;
import com.springbootvue.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/guest")
public class UserController {

    final private UserService userService; //UserDAO bean을 자동으로 주입

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    /** 사용자 정보 저장(회원가입) **/
    @PostMapping("/setUserInfo")
    public void setUserInfo(@RequestBody UserDTO userDTO){

        userService.setUserInfo(userDTO);
    }

    /** 사용자 아이디 중복 검사 **/
    @GetMapping("/chkDuplicatedId")
    public boolean chkDuplicatedId(@RequestParam(value = "userId") String userId){

        return userService.chkDuplicatedId(userId);
    }
}
