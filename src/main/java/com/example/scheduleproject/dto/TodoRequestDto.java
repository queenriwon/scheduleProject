package com.example.scheduleproject.dto;

import lombok.Getter;

@Getter
public class TodoRequestDto {
    private String name;
    private String email;
    private String todo;
    private String password;
    private String passwordCheck;
}
