package com.example.scheduleproject.service;

import com.example.scheduleproject.dto.PageResponseDto;
import com.example.scheduleproject.dto.TodoRequestPostDto;
import com.example.scheduleproject.dto.TodoRequestGetDto;
import com.example.scheduleproject.dto.TodoResponseDto;

public interface ScheduleService {
    TodoResponseDto createTodo(TodoRequestPostDto dto);

    PageResponseDto<TodoResponseDto> findTodoByNameOrUpdatedAt(TodoRequestGetDto dto, int page, int size);

    PageResponseDto<TodoResponseDto> findTodoAll(int page, int size);

    TodoResponseDto findTodoById(Long id);

    TodoResponseDto updateNameAndTodo(Long id, String name, String todo, String password);

    void deleteTodoById(Long id, String password);

}
