package com.example.scheduleproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ScheduleEntity {
    private Long id;
    private Long user_id;
    private String todo;
    private String password;
    private String createdAt;
    private String updatedAt;

    public ScheduleEntity(Long user_id, String todo, String password, String createdAt, String updatedAt) {
        this.user_id = user_id;
        this.todo = todo;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public ScheduleEntity(String todo, String password) {
        this.todo = todo;
        this.password = password;
    }
}