package com.example.scheduleproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoRequestGetDto {
    private String name;
    private String updatedAtFrom;
    private String updatedAtTo;
}
