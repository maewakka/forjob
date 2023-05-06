package com.woo.forjob.dto.auth;

import com.woo.forjob.util.validation.ValidationGroup;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignUpRequestDto {

    @Email(message = "email 형식이 아닙니다.", groups = ValidationGroup.EmailCheckGroup.class)
    @NotBlank(message = "빈 항목을 채워주세요.", groups = ValidationGroup.NotBlankGroup.class)
    private String email;

    @Size(min = 8, message = "비밀번호가 너무 짧습니다.", groups = ValidationGroup.SizeCheckGroup.class)
    @NotBlank(message = "빈 항목을 채워주세요.", groups = ValidationGroup.NotBlankGroup.class)
    private String password;

    @NotBlank(message = "빈 항목을 채워주세요.", groups = ValidationGroup.NotBlankGroup.class)
    private String nickname;
}
