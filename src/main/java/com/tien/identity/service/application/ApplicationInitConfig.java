package com.tien.identity.service.application;

import com.tien.identity.service.entity.User;
import com.tien.identity.service.enums.Roles;
import com.tien.identity.service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
@EnableJpaAuditing
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;
    //tao 1 user admin ngay khi chạy application
    @Bean
    //chi chay init nay neu khong phai test vi test su dung h2 => sai cu phap sql
    @ConditionalOnProperty(prefix = "spring",
    value = "datasource.driverClassName",
    havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner(UserRepository userRepository){
       return  args -> {
           if(userRepository.findByUsername("admin").isEmpty()){
               var roles = new HashSet<String>();
               roles.add(Roles.ADMIN.name());
               User user = User.builder()
                       .username("admin")
//                       .roles(roles)
                       .password(passwordEncoder.encode("admin"))
                       .build();
               userRepository.save(user);
               log.warn("admin user has been created with default password: admin, please change it");
           }
       };
    }
}
