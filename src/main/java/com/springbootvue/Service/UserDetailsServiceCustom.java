package com.springbootvue.Service;

import com.springbootvue.dao.UserDAO;
import com.springbootvue.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static javax.swing.JOptionPane.showMessageDialog;

@Service
public class UserDetailsServiceCustom implements UserDetailsService{

    /** 사용자 DAO **/
    final private UserDAO userDAO;

    /** 비밀번호 인코딩을 위해 **/
    final private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserDetailsServiceCustom(UserDAO userDAO, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDAO = userDAO;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /** UserDetailsService overriding method **/
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

    /** 사용자 정보 저장하기(회원가입) **/
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void setUserInfo(UserDTO userDTO){
        userDTO.setPwd(bCryptPasswordEncoder.encode(userDTO.getPwd()));
        int rs = userDAO.insertUserInfo(userDTO);
        if(rs>0){
            int userSid = userDAO.selectUserSid(userDTO.getId());
            int roleSid = userDAO.selectRoleSid(userDTO.getRoleName());
            userDAO.insertUserRole(userSid, roleSid);
        } else {
            showMessageDialog(null, "회원등록에 실패하였습니다.");
        }
    }
    
    /** 사용자 아이디 중복 체크하기 **/
    public boolean chkDuplicatedId(String userId){

        return userDAO.selectUserSid(userId) == null ? false : true;
    }

    /** 로그인 기능 구현하기 **/
    public UserDTO login(String inputId, String inputPwd){
        UserDTO userDTO = userDAO.selectUserInfo(inputId);

        if(userDTO == null){
            return null; // 가입하지 않은 아이디입니다.
        } else {
            if(bCryptPasswordEncoder.matches(inputPwd, userDTO.getPwd())){
                return userDTO; // 로그인 성공
            } else {
                return null; // 잘못된 비밀번호입니다.
            }
        }
    }

    /** 사용자ID로 회원정보 찾기 **/
    public UserDTO getUserInfo(String userId) {

        return userDAO.selectUserInfo(userId);
    }
}
