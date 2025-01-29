package com.example.scheduleproject.repository;

import com.example.scheduleproject.dto.TodoRequestDto;
import com.example.scheduleproject.dto.TodoResponseDto;
import com.example.scheduleproject.entity.TodosEntity;
import com.example.scheduleproject.mapper.ToRowMapper;
import lombok.extern.slf4j.Slf4j;
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
public class TodosRepositoryImpl implements TodosRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ToRowMapper toRowMapper;

    public TodosRepositoryImpl(JdbcTemplate jdbcTemplate, ToRowMapper toRowMapper) {
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
    public Optional<TodosEntity> findTodoById(Long id) {
        List<TodosEntity> todosEntityList = jdbcTemplate.query("select * from todos where id = ?", toRowMapper.todosRowMapper(), id);
        return todosEntityList.stream().findAny();
    }

    @Override
    public Optional<TodosEntity> findTodoByIdAndAuthorize(Long id, String password) {
        List<TodosEntity> todosEntityList = jdbcTemplate.query("select * from todos where id = ?", toRowMapper.todosRowMapper(), id);
        if (todosEntityList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found id");
        }

        TodosEntity todosEntity = todosEntityList.get(0);
        checkPassword(todosEntity.getPassword(), password);
        return Optional.of(todosEntity);
    }

    @Override
    public void updateTodo(Long id, String todo) {
        jdbcTemplate.update("update todos set todo = ? where id = ?", todo, id);
    }

    @Override
    public void deleteTodoById(Long id, String password) {
        findTodoByIdAndAuthorize(id, password);
        jdbcTemplate.update("delete from todos where id = ?", id);
    }

    private void checkPassword(String storedPassword, String password) {
        if (!storedPassword.equals(password))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호 확인");
    }

    private String getNowDatetime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        return sdf.format(now);
    }
}
