package com.example.scheduleproject.repository;

import com.example.scheduleproject.dto.TodoRequestDto;
import com.example.scheduleproject.dto.TodoResponseDto;
import com.example.scheduleproject.entity.TodosEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TodoResponseDto createTodo(TodoRequestDto dto) {

        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users WHERE email = ?", Integer.class, dto.getEmail());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String nowTime = sdf.format(now);

        Number userId;

        if (count > 0) {
            String sql = "SELECT id FROM users WHERE email = ?";
            userId = jdbcTemplate.queryForObject(sql, Long.class, dto.getEmail());

        } else {
            SimpleJdbcInsert jdbcInsertUser = new SimpleJdbcInsert(jdbcTemplate);
            jdbcInsertUser.withTableName("users").usingGeneratedKeyColumns("id");

            Map<String, Object> parametersUser = new HashMap<>();
            parametersUser.put("name", dto.getName());
            parametersUser.put("email", dto.getEmail());
            parametersUser.put("created_at", nowTime);
            parametersUser.put("updated_at", nowTime);

            userId = jdbcInsertUser.executeAndReturnKey(new MapSqlParameterSource(parametersUser));
        }

        SimpleJdbcInsert jdbcInsertTodos = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsertTodos.withTableName("todos").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", userId.longValue());
        parameters.put("todo", dto.getTodo());
        parameters.put("password", dto.getPassword());
        parameters.put("created_at", nowTime);
        parameters.put("updated_at", nowTime);

        Number key = jdbcInsertTodos.executeAndReturnKey(new MapSqlParameterSource(parameters));

        TodosEntity result = jdbcTemplate.query("SELECT * FROM todos WHERE id = ?", todoRowMapper(), key).get(0);

        return new TodoResponseDto(key.longValue(), dto.getName(), dto.getEmail(), result.getTodo(), result.getCreatedAt(), result.getUpdatedAt());
    }

    @Override
    public List<TodoResponseDto> findTodoByNameAndUpdatedAt(String name, String updatedAtFrom, String updatedAtTo) {

        StringBuilder sb = new StringBuilder("select * from todos where 1=1");
        List<Object> list = new ArrayList<>();

        if (name != null) {
            sb.append(" name = ?");
            list.add(name);
        }

        if (updatedAtFrom != null && updatedAtTo != null) {
            sb.append(" and (date(?) <= date(updated_at) and date(updated_at) <= date(?))");
            list.add(updatedAtFrom);
            list.add(updatedAtTo);
        } else if (updatedAtFrom != null) {
            sb.append(" and (date(?) <= date(updated_at)");
            list.add(updatedAtFrom);
        } else if (updatedAtTo != null) {
            sb.append(" and (date(updated_at) <= date(?))");
            list.add(updatedAtTo);
        }

        sb.append("order by updated_at desc");

        List<TodoResponseDto> result = jdbcTemplate.query(sb.toString(),
                todoResponseDtoRowMapper(), list.toArray());

        result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "검색결과를 찾을 수 없음"));
        return result;
    }

    @Override
    public List<TodoResponseDto> findTodoAll() {
        return jdbcTemplate.query("select * from todos order by updated_at desc", todoResponseDtoRowMapper());
    }

    @Override
    public TodoResponseDto findTodoByIdElseThrow(Long id) {
        List<TodoResponseDto> result = jdbcTemplate.query("select * from todos where id = ?", todoResponseDtoRowMapper(), id);

        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "찾을 수 없는 id = " + id));
    }

    @Override
    public int updateNameAndTodo(Long id, String name, String todo, String password) {
        String storedPassword = jdbcTemplate.queryForObject("select password from todos where id = ?", String.class, id);

        if (!storedPassword.equals(password))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호 확인");

        return jdbcTemplate.update("update todos set name = ?, todo = ? where id = ?", name, todo, id);
    }

    @Override
    public void deleteTodoById(Long id, String password) {
        try {
            String storedPassword = jdbcTemplate.queryForObject("select password from todos where id = ?", String.class, id);
            if (!storedPassword.equals(password))
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호 확인");
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "없는 id 값");
        }
        jdbcTemplate.update("delete from todos where id = ?", id);
    }


    private RowMapper<TodosEntity> todoRowMapper() {
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

    private RowMapper<TodoResponseDto> todoResponseDtoRowMapper() {
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
