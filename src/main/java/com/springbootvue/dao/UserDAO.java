package com.springbootvue.dao;

import com.springbootvue.dto.UserDTO;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface UserDAO {
    // 사용자 정보 찾기
    ArrayList<UserDTO> findByUserId(@Param("id") String id);

    // 회원가입 - 사용자 정보 저장
    int saveUserInfo(UserDTO userDTO);

    // 사용자 권한 저장
    int saveUserRole(int uid, int rid);

    // 사용자 FK번호 찾기
    int findUserSid(@Param("id") String id);

    // 권한 FK번호 찾기
    int findRoleSid(@Param("roleName") String roleName);

    List<UserDTO> selectUsers(UserDTO param) throws Exception;
}