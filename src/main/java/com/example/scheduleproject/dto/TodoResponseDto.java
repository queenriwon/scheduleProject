package com.example.scheduleproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TodoResponseDto {
    private Long id;
    private String name;
    private String email;
    private String todo;
    private String createdAt;
    private String updatedAt;

    public TodoResponseDto(Long id, String todo, String createdAt, String updatedAt) {
        this.id = id;
        this.todo = todo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
