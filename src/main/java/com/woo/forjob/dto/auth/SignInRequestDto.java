package com.woo.forjob.dto.auth;

import com.woo.forjob.util.validation.ValidationGroup;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class SignInRequestDto {

    @Email(message = "email 형식이 아닙니다.", groups = ValidationGroup.EmailCheckGroup.class)
    @NotBlank(message = "빈 항목을 채워주세요.", groups = ValidationGroup.NotBlankGroup.class)
    private String email;

    @NotBlank(message = "빈 항목을 채워주세요.", groups = ValidationGroup.NotBlankGroup.class)
    private String password;

    @Builder
    public SignInRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
