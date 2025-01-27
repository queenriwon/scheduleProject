package com.example.scheduleproject.service;

import com.example.scheduleproject.dto.TodoRequestDto;
import com.example.scheduleproject.dto.TodoResponseDto;
import com.example.scheduleproject.dto.TodoGetRequestDto;
import java.util.List;

public interface ScheduleService {
    TodoResponseDto createTodo(TodoRequestDto dto);
    List<TodoResponseDto> findTodoByNameOrUpdatedAt(String name, String updatedAtFrom, String updatedAtTo);
}
