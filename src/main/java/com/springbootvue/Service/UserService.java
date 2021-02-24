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

import static javax.swing.JOptionPane.showMessageDialog;

@Service
public class UserService implements UserDetailsService{

    /** 사용자 DAO **/
    final private UserDAO userDAO;

    /** 비밀번호 인코딩을 위해 **/
    final private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserDAO userDAO, BCryptPasswordEncoder bCryptPasswordEncoder) {
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
}
