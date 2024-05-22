package com.tien.identity.service.entity;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Table
@Entity()
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    // tao ra cac id ngau nhien khong trung lap(phuc tap hon 1, 2, 3,...)
    @GeneratedValue(strategy = GenerationType.UUID)
     String id;

     String username;
     String password;
     String firstname;
     String lastname;
     LocalDate dob;
     Set<String> roles;



    
}
