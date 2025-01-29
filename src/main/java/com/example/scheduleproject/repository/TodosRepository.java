package com.example.scheduleproject.repository;

import com.example.scheduleproject.dto.TodoRequestDto;
import com.example.scheduleproject.dto.TodoResponseDto;
import com.example.scheduleproject.entity.TodosEntity;
import com.example.scheduleproject.mapper.ToRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Repository
public class TodosRepository implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ToRowMapper toRowMapper;

    public TodosRepository(JdbcTemplate jdbcTemplate, ToRowMapper toRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.toRowMapper = toRowMapper;
    }

    @Override
    public TodosEntity createTodo(Long userId, TodoRequestDto dto) {
        SimpleJdbcInsert jdbcInsertTodos = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsertTodos.withTableName("todos").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", userId);
        parameters.put("todo", dto.getTodo());
        parameters.put("password", dto.getPassword());
        parameters.put("created_at", getNowDatetime());
        parameters.put("updated_at", getNowDatetime());

        Number key = jdbcInsertTodos.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return jdbcTemplate.query("SELECT * FROM todos WHERE id = ?", toRowMapper.todosRowMapper(), key).get(0);
    }

    private String getNowDatetime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        return sdf.format(now);
    }

    @Override
    public List<TodoResponseDto> findTodoByNameAndUpdatedAt(List<Long> userIdList, String updatedAtFrom, String updatedAtTo) {
        StringBuilder sb = new StringBuilder(
                "select a.id, b.name, b.email, a.todo, a.created_at, a.updated_at" +
                        " from todos a join users b on a.user_id = b.id where 1=1");
        List<Object> list = new ArrayList<>();

        if (!userIdList.isEmpty()) {
            sb.append(" and a.user_id IN (?");
            list.add(userIdList.get(0));
            for (int i = 1; i < userIdList.size(); i++) {
                sb.append(", ?");
                list.add(userIdList.get(i));
            }
            sb.append(")");
        }

        if (updatedAtFrom != null && updatedAtTo != null) {
            sb.append(" and (a.updated_at between ? and ?)");
            list.add(updatedAtFrom);
            list.add(updatedAtTo);
        } else if (updatedAtFrom != null) {
            sb.append(" and a.updated_at >= ?");
            list.add(updatedAtFrom);
        } else if (updatedAtTo != null) {
            sb.append(" and a.updated_at <= ?");
            list.add(updatedAtTo);
        }

        sb.append(" order by updated_at desc");

        return jdbcTemplate.query(sb.toString(), toRowMapper.todoResponseDtoRowMapper(), list.toArray());
    }

    @Override
    public List<TodoResponseDto> findTodoAll() {
        return jdbcTemplate.query(
                "select a.id, b.name, b.email, a.todo, a.created_at, a.updated_at" +
                        " from todos a join users b on a.user_id = b.id" +
                        " order by updated_at desc", toRowMapper.todoResponseDtoRowMapper());
    }

    @Override
    public TodoResponseDto findTodoByIdElseThrow(Long id) {
        List<TodoResponseDto> result = jdbcTemplate.query(
                "select a.id, b.name, b.email, a.todo, a.created_at, a.updated_at" +
                        " from todos a join users b on a.user_id = b.id where a.id = ?", toRowMapper.todoResponseDtoRowMapper(), id);

        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "찾을 수 없는 id = " + id));
    }

    @Override
    public int updateNameAndTodo(Long id, String name, String todo, String password) {
        String storedPassword = jdbcTemplate.queryForObject("select password from todos where id = ?", String.class, id);

        if (!storedPassword.equals(password))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호 확인");

        Long userId = jdbcTemplate.queryForObject("select user_id from todos where id = ?", Long.class, id);
        jdbcTemplate.update("update users set name = ? where id = ?", name, userId);

        return jdbcTemplate.update("update todos set todo = ? where user_id = ?", todo, userId);
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
}
