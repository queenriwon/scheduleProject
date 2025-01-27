package com.example.scheduleproject.repository;

import com.example.scheduleproject.dto.TodoResponseDto;
import com.example.scheduleproject.dto.TodoRequestDto;

public interface ScheduleRepository {
    TodoResponseDto createTodo(TodoRequestDto dto);
}
