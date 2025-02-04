package com.example.scheduleproject.mapper;

import com.example.scheduleproject.dto.TodoRequestPostDto;
import com.example.scheduleproject.dto.TodoResponseDto;
import com.example.scheduleproject.entity.TodosEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodosToMapper {

    TodoResponseDto toDto(TodosEntity e);

    TodoResponseDto toDto(TodosEntity e, String name, String email);

    TodosEntity toEntity(TodoRequestPostDto dto);
}