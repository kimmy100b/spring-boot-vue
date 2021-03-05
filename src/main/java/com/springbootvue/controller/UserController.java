package com.springbootvue.controller;

import com.springbootvue.Service.UserDetailsServiceCustom;
import com.springbootvue.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/guest")
public class UserController {

    final private UserDetailsServiceCustom userDetailsServiceCustom; //UserDAO bean을 자동으로 주입

    @Autowired
    public UserController(UserDetailsServiceCustom userDetailsServiceCustom) {
        this.userDetailsServiceCustom = userDetailsServiceCustom;
    }


    /** 사용자 정보 저장(회원가입) **/
    @PostMapping("/setUserInfo")
    public void setUserInfo(@RequestBody UserDTO userDTO){

        userDetailsServiceCustom.setUserInfo(userDTO);
    }

    /** 사용자 아이디 중복 검사 **/
    @GetMapping("/chkDuplicatedId")
    public boolean chkDuplicatedId(@RequestParam(value = "userId") String userId){

        return userDetailsServiceCustom.chkDuplicatedId(userId);
    }

    /** 로그인 기능 **/
    @PostMapping("/login")
    public UserDTO login(@RequestBody UserDTO getUserDTO, HttpServletRequest request){
        UserDTO userDTO = userDetailsServiceCustom.login(getUserDTO.getId(), getUserDTO.getPwd());
     /*
        if(isLogin){
            // 로그인 성공 시 세션에 회원정보 저장
            UserDTO userInfo = userDetailsServiceCustom.getUserInfo(userDTO.getId());
            HttpSession session = request.getSession();
            session.setAttribute("isLogOn", true);
            session.setAttribute("userInfo", userInfo);
        }
        */

        return userDTO;
    }
}
