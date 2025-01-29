package com.example.scheduleproject.mapper;

import com.example.scheduleproject.dto.TodoResponseDto;
import com.example.scheduleproject.entity.TodosEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ToRowMapper {

    public RowMapper<TodosEntity> todosRowMapper() {
        return new RowMapper<TodosEntity>() {
            @Override
            public TodosEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new TodosEntity(
                        rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getString("todo"),
                        rs.getString("password"),
                        rs.getString("created_at"),
                        rs.getString("updated_at")
                );
            }
        };
    }

    public RowMapper<TodoResponseDto> todoResponseDtoRowMapper() {
        return new RowMapper<TodoResponseDto>() {
            @Override
            public TodoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new TodoResponseDto(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("todo"),
                        rs.getString("created_at"),
                        rs.getString("updated_at")
                );
            }
        };
    }
}
