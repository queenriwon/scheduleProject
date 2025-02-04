package com.example.scheduleproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class TodoRequestPostDto {

    @NotBlank(message = "name은 필수 입력값 입니다.")
    private String name;

    @Email(message = "email형태로 입력 가능합니다.")
    @NotBlank(message = "email은 필수 입력값 입니다.")
    private String email;

    @NotBlank(message = "todo는 필수 입력값 입니다.")
    @Size(max = 200, message = "todo는 200자까지 작성 가능합니다.")
    private String todo;

    @NotBlank(message = "password는 필수 입력값 입니다.")
    private String password;
    @NotBlank(message = "passwordCheck는 필수 입력값 입니다.")
    private String passwordCheck;

    public boolean areAllNotNull() {
        return name != null && email != null && todo != null && password != null && passwordCheck != null;
    }
}
