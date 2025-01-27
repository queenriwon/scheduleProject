package com.example.scheduleproject.repository;

import com.example.scheduleproject.dto.TodoRequestDto;
import com.example.scheduleproject.dto.TodoResponseDto;
import com.example.scheduleproject.entity.Todos;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository{

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TodoResponseDto createTodo(TodoRequestDto dto) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("todos").usingGeneratedKeyColumns("id");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String nowTime = sdf.format(now);

        Todos todos = new Todos(dto.getName(), dto.getTodo(), dto.getPassword());
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", todos.getName());
        parameters.put("todo", todos.getTodo());
        parameters.put("password", todos.getPassword());
        parameters.put("created_at", nowTime);
        parameters.put("updated_at", nowTime);

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        Todos result = jdbcTemplate.query("select * from todos where id = ?", todosRowMapper(), key).get(0);

        return new TodoResponseDto(key.longValue(), result.getName(), result.getTodo(), result.getCreatedAt(), result.getUpdatedAt());
    }

    private RowMapper<Todos> todosRowMapper() {
        return new RowMapper<Todos>() {
            @Override
            public Todos mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Todos(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("todo"),
                        rs.getString("password"),
                        rs.getString("created_at"),
                        rs.getString("updated_at")
                );
            }
        };
    }
}
