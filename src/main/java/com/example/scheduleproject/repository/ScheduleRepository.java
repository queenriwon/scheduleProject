package com.example.scheduleproject.repository;

import com.example.scheduleproject.dto.TodoResponseDto;
import com.example.scheduleproject.entity.ScheduleEntity;

import java.util.List;

public interface ScheduleRepository {
    TodoResponseDto createTodo(ScheduleEntity scheduleEntity);

    List<TodoResponseDto> findTodoByNameAndUpdatedAt(String name, String updatedAtFrom, String updatedAtTo);

    List<TodoResponseDto> findTodoAll();

    TodoResponseDto findTodoByIdElseThrow(Long id);

    int updateNameAndTodo(Long id, String name, String todo, String password);

    void deleteTodoById(Long id, String password);

}
