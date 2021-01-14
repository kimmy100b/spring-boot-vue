package com.springbootvue.Service;

import com.springbootvue.dao.UserDAO;
import com.springbootvue.dto.UserDTO;
import com.springbootvue.dto.UserPrincipalVO;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;

@Service
public class UserService implements UserDetailsService {

    private final UserDAO userDAO;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserDAO userDAO, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDAO = userDAO;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        ArrayList<UserDTO> userAuthes = userDAO.findByUserId(id);

        if(userAuthes.size() == 0){
            throw new UsernameNotFoundException("아이디가 "+id+"인 회원을 찾을 수 없습니다.");
        }
        return new UserPrincipalVO(userAuthes);
    }

    // 사용자 정보 저장(회원가입)
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void saveUserInfo(UserDTO userDTO){
        userDTO.setPwd(bCryptPasswordEncoder.encode(userDTO.getPwd()));
        int rs = userDAO.saveUserInfo(userDTO);

        if(rs>0){
            int userSid = userDAO.findUserSid(userDTO.getId());
            int roleSid = userDAO.findRoleSid(userDTO.getRoleName());

            userDAO.saveUserRole(userSid, roleSid);
        } else {
            showMessageDialog(null, "회원등록에 실패하였습니다.");
        }

    }
}
