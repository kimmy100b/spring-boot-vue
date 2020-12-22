package com.springbootvue.config;

import com.springbootvue.dao.UserDAO;
import com.springbootvue.dto.UserDTO;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.List;

@Configuration
public class DataSourceConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

//    @Bean
//    public UserDAO userDAO(){
//        return new UserDAO() {
//            @Override
//            public List<UserDTO> selectUsers(UserDTO param) throws Exception {
//                return null;
//            }
//        };
}