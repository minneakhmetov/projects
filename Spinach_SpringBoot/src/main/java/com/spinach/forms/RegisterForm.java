package com.spinach.forms;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@Setter
public class RegisterForm {
    String name;
    String surname;
    String birthdate;
    String email;
    String password;
    String city;
    MultipartFile photo;
}
