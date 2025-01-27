package com.example.scheduleproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Todos {
    private Long id;
    private String name;
    private String todo;
    private String password;
    private String createdAt;
    private String updatedAt;
}