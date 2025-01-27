package com.example.scheduleproject.repository;

import com.example.scheduleproject.dto.TodoResponseDto;
import com.example.scheduleproject.dto.TodoRequestDto;

import java.util.List;

public interface ScheduleRepository {
    TodoResponseDto createTodo(TodoRequestDto dto);

    List<TodoResponseDto> findTodoByNameAndUpdatedAt(String name, String updatedAtFrom, String updatedAtTo);

    List<TodoResponseDto> findTodoByName(String name);

    List<TodoResponseDto> findTodoByUpdatedAt(String updatedAtFrom, String updatedAtTo);

    TodoResponseDto findTodoByIdElseThrow(Long id);

}
