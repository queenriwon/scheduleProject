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
    private Long userId;
    private String todo;
    private String password;
    private String createdAt;
    private String updatedAt;
}