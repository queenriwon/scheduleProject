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

    public Todos(String name, String todo, String password, String createdAt, String updatedAt) {
        this.name = name;
        this.todo = todo;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Todos(String name, String todo, String password) {
        this.name = name;
        this.todo = todo;
        this.password = password;
    }
}