package com.example.scheduleproject.service;

import com.example.scheduleproject.dto.TodoRequestDto;
import com.example.scheduleproject.dto.TodoResponseDto;

public interface ScheduleService {
    TodoResponseDto createTodo(TodoRequestDto dto);
}
