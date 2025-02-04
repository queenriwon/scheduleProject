package com.example.scheduleproject.repository;

import com.example.scheduleproject.dto.PageResponseDto;
import com.example.scheduleproject.dto.TodoResponseDto;
import com.example.scheduleproject.entity.TodosEntity;

import java.util.Optional;

public interface TodosRepository {
    TodosEntity createTodo(Long userId, TodosEntity dto);

    PageResponseDto<TodoResponseDto> findTodoByNameAndUpdatedAt(Long userId, String updatedAtFrom, String updatedAtTo, int page, int size);

    PageResponseDto<TodoResponseDto> findTodoAll(int page, int size);

    Optional<TodosEntity> findTodoById(Long id);

    Optional<TodosEntity> findTodoByIdAndAuthorize(Long id, String password);

    void updateTodo(Long id, String todo);

    void deleteTodoById(Long id, String password);

}
