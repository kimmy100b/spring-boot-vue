package com.springbootvue.dao;

import com.springbootvue.dto.UserDTO;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface UserDAO {
    /** 사용자 정보 찾기 **/
    ArrayList<UserDTO> selectUsers(@Param("id") String id);

    /** 회원가입 - 사용자 정보 저장 **/
    Integer insertUserInfo(UserDTO userDTO);

    /** 사용자 권한 저장 **/
    Integer insertUserRole(
            @Param("uid") Integer uid,
            @Param("rid") Integer rid);

    /** 사용자 FK번호 찾기 **/
    Integer selectUserSid(@Param("id") String id);

    /** 권한 FK번호 찾기 **/
    Integer selectRoleSid(@Param("roleName") String roleName);
}
