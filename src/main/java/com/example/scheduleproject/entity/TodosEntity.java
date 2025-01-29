package com.example.scheduleproject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TodosEntity {

    private Long id;
    private Long user_id;
    private String todo;
    private String password;
    private String createdAt;
    private String updatedAt;


    public TodosEntity(Long user_id, String todo, String password, String createdAt, String updatedAt) {
        this.user_id = user_id;
        this.todo = todo;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public TodosEntity(String todo, String password) {
        this.todo = todo;
        this.password = password;
    }
}