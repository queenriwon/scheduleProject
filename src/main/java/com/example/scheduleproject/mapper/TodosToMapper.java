package com.example.scheduleproject.mapper;

import com.example.scheduleproject.dto.TodoRequestDto;
import com.example.scheduleproject.dto.TodoResponseDto;
import com.example.scheduleproject.entity.TodosEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodosToMapper {

    TodoResponseDto toDTO(TodosEntity e);
    TodoResponseDto toDTO(TodosEntity e, String name, String email);
    TodosEntity toEntity(TodoRequestDto dto);
}