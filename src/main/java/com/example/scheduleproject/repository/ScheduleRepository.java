package com.example.scheduleproject.repository;

import com.example.scheduleproject.dto.TodoRequestDto;
import com.example.scheduleproject.dto.TodoResponseDto;
import com.example.scheduleproject.entity.TodosEntity;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    TodosEntity createTodo(Long userId, TodoRequestDto dto);

    List<TodoResponseDto> findTodoByNameAndUpdatedAt(List<Long> userIdList, String updatedAtFrom, String updatedAtTo);

    List<TodoResponseDto> findTodoAll();

    Optional<TodosEntity> findTodoById(Long id);

    int updateNameAndTodo(Long id, String name, String todo, String password);

    void deleteTodoById(Long id, String password);

}
