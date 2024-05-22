package com.tien.identity.service.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 8, max = 15, message = "USERNAME_INVALID")
     String username;

    @Size(min = 8, max = 15, message = "PASSWORD_INVALID")
     String password;

     String firstname;
     String lastname;
     LocalDate dob;


}
