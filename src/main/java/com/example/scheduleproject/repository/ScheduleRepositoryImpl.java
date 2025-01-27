package com.example.scheduleproject.repository;

import com.example.scheduleproject.dto.TodoRequestDto;
import com.example.scheduleproject.dto.TodoResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import com.example.scheduleproject.entity.Todo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

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

        Todo todo = new Todo(dto.getName(), dto.getTodo(), dto.getPassword());
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", todo.getName());
        parameters.put("todo", todo.getTodo());
        parameters.put("password", todo.getPassword());
        parameters.put("created_at", nowTime);
        parameters.put("updated_at", nowTime);

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        Todo result = jdbcTemplate.query("select * from todos where id = ?", todosRowMapper(), key).get(0);

        return new TodoResponseDto(key.longValue(), result.getName(), result.getTodo(), result.getCreatedAt(), result.getUpdatedAt());
    }

    @Override
    public List<TodoResponseDto> findTodoByNameAndUpdatedAt(String name, String updatedAtFrom, String updatedAtTo) {
        List<TodoResponseDto> result = jdbcTemplate.query("select * from todos where name = ? and (date(?) <= date(updated_at) and date(updated_at) <= date(?))",
                todoResponseDtoRowMapper(), name, updatedAtFrom, updatedAtTo);

        result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "검색결과를 찾을 수 없음"));
        return result;
    }

    @Override
    public List<TodoResponseDto> findTodoByName(String name) {
        List<TodoResponseDto> result = jdbcTemplate.query("select * from todos where name = ?",
                todoResponseDtoRowMapper(), name);

        result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "검색결과를 찾을 수 없음"));
        return result;
    }

    @Override
    public List<TodoResponseDto> findTodoByUpdatedAt(String updatedAtFrom, String updatedAtTo) {
        List<TodoResponseDto> result = jdbcTemplate.query("select * from todos where date(?) <= date(updated_at) and date(updated_at) <= date(?)",
                todoResponseDtoRowMapper(), updatedAtFrom, updatedAtTo);

        result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "검색결과를 찾을 수 없음"));
        return result;
    }

    @Override
    public TodoResponseDto findTodoByIdElseThrow(Long id) {
        List<TodoResponseDto> result = jdbcTemplate.query("select * from todos where id = ?", todoResponseDtoRowMapper(), id);

        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "찾을 수 없는 id = " + id));
    }

    private RowMapper<Todo> todosRowMapper() {
        return new RowMapper<Todo>() {
            @Override
            public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Todo(
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

    private RowMapper<TodoResponseDto> todoResponseDtoRowMapper() {
        return new RowMapper<TodoResponseDto>() {
            @Override
            public TodoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new TodoResponseDto(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("todo"),
                        rs.getString("created_at"),
                        rs.getString("updated_at")
                );
            }
        };
    }
}
