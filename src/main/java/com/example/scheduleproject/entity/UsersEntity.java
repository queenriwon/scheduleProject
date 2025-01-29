package com.example.scheduleproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsersEntity {
    private Long id;
    private String name;
    private String email;
    private String created_at;
    private String updated_at;

    public UsersEntity(String name, String email) {
        this.name = name;
        this.email = email;
    }
}