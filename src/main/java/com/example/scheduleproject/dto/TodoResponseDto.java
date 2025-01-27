package com.example.scheduleproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class TodoResponseDto {
    private Long id;
    private String name;
    private String todo;
    private String createdAt;
    private String updatedAt;
}
