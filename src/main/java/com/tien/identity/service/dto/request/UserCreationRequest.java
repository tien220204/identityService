package com.tien.identity.service.dto.request;

import java.time.LocalDate;
import java.util.HashSet;

import com.tien.identity.service.entity.Role;
import com.tien.identity.service.validator.DobConstraint;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @Size(min = 8, max = 15, message = "PASSWORD_INVALID")
     String password;

     String firstname;
     String lastname;

     @DobConstraint(min = 18, message = "DOB_INVALID")
     LocalDate dob;


}
