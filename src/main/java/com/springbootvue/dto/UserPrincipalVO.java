package com.springbootvue.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrincipalVO implements UserDetails {

    private ArrayList<UserDTO> userDTO;

    @Autowired
    public UserPrincipalVO(ArrayList<UserDTO> userDTO) {
        this.userDTO = userDTO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //유저가 갖고 있는 권한 목록

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for(int x=0; x<userDTO.size(); x++) {
            authorities.add(new SimpleGrantedAuthority(userDTO.get(x).getRoleName()));
        }

        return authorities;
    }

    @Override
    public String getPassword() { //유저 비밀번호

        return userDTO.get(0).getPwd();
    }


    @Override
    public String getUsername() {// 유저 이름 혹은 아이디
        return userDTO.get(0).getId();
//        return userDTO.get(0).getName();
    }

    @Override
    public boolean isAccountNonExpired() {// 유저 아이디가 만료 되었는지

        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // 유저 아이디가 Lock 걸렸는지

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { //비밀번호가 만료 되었는지

        return true;
    }

    @Override
    public boolean isEnabled() { // 계정이 활성화 되었는지

        return true;
    }


}

