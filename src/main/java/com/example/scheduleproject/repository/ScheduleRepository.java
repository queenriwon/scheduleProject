package com.example.scheduleproject.repository;

import com.example.scheduleproject.dto.TodoRequestDto;
import com.example.scheduleproject.dto.TodoResponseDto;
import com.example.scheduleproject.entity.TodosEntity;

import java.util.List;

public interface ScheduleRepository {
    TodosEntity createTodo(TodoRequestDto dto);

    List<TodoResponseDto> findTodoByNameAndUpdatedAt(String name, String updatedAtFrom, String updatedAtTo);

    List<TodoResponseDto> findTodoAll();

    TodoResponseDto findTodoByIdElseThrow(Long id);

    int updateNameAndTodo(Long id, String name, String todo, String password);

    void deleteTodoById(Long id, String password);

}
