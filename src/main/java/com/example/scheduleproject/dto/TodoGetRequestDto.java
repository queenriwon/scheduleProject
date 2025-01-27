package com.example.scheduleproject.dto;

import lombok.Getter;

@Getter
public class TodoGetRequestDto {
    private String name;
    private String updatedAtFrom;
    private String updatedAtTo;
}
