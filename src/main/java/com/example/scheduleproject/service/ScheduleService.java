package com.example.scheduleproject.service;

import com.example.scheduleproject.dto.TodoRequestDto;
import com.example.scheduleproject.dto.TodoResponseDto;

import java.util.List;

public interface ScheduleService {
    TodoResponseDto createTodo(TodoRequestDto dto);

    List<TodoResponseDto> findTodoByNameOrUpdatedAt(String name, String updatedAtFrom, String updatedAtTo);

    List<TodoResponseDto> findTodoAll();

    TodoResponseDto findTodoById(Long id);

    TodoResponseDto updateNameAndTodo(Long id, String name, String todo, String password);

    void deleteTodoById(Long id);
}
