package com.example.scheduleproject.repository;

import com.example.scheduleproject.dto.PageResponseDto;
import com.example.scheduleproject.dto.TodoRequestDto;
import com.example.scheduleproject.dto.TodoResponseDto;
import com.example.scheduleproject.entity.TodosEntity;
import com.example.scheduleproject.exception.IdNotFoundException;
import com.example.scheduleproject.exception.PasswordMismatchException;
import com.example.scheduleproject.mapper.ToRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

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
    public PageResponseDto<TodoResponseDto> findTodoByNameAndUpdatedAt(List<Long> userIdList, String updatedAtFrom, String updatedAtTo, int page, int size) {
        StringBuilder sb = new StringBuilder(
                "select a.id, b.name, b.email, a.todo, a.created_at, a.updated_at" +
                        " from todos a join users b on a.user_id = b.id where 1=1");
        List<Object> list = new ArrayList<>();

        if (!userIdList.isEmpty()) {
            sb.append(" and a.user_id IN (");
            for (int i = 0; i < userIdList.size(); i++) {
                sb.append("?");
                if (i < userIdList.size() - 1) {
                    sb.append(", ");
                }
                list.add(userIdList.get(i));
            }
            sb.append(")");
        }

        if (updatedAtFrom != null && updatedAtTo != null) {
            sb.append(" and (date(a.updated_at) between ? and ?)");
            list.add(updatedAtFrom);
            list.add(updatedAtTo);
        } else if (updatedAtFrom != null) {
            sb.append(" and date(a.updated_at) >= ?");
            list.add(updatedAtFrom);
        } else if (updatedAtTo != null) {
            sb.append(" and date(a.updated_at) <= ?");
            list.add(updatedAtTo);
        }

        int totalElements = jdbcTemplate.query(sb.toString(), toRowMapper.todoResponseDtoRowMapper(), list.toArray()).size();

        sb.append(" order by updated_at desc limit ? offset ?");
        list.add(size);
        list.add(page * size);

        List<TodoResponseDto> todoResponseDtoList = jdbcTemplate.query(sb.toString(), toRowMapper.todoResponseDtoRowMapper(), list.toArray());

        return new PageResponseDto<>(todoResponseDtoList, page, size, totalElements);
    }

    @Override
    public PageResponseDto<TodoResponseDto> findTodoAll(int page, int size) {
        List<TodoResponseDto> todoResponseDtoList = jdbcTemplate.query(
                "select a.id, b.name, b.email, a.todo, a.created_at, a.updated_at" +
                        " from todos a join users b on a.user_id = b.id" +
                        " order by updated_at desc limit ? offset ?", toRowMapper.todoResponseDtoRowMapper(), size, page * size);
        return new PageResponseDto<>(todoResponseDtoList, page, size, returnToTalElements());
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
            throw new IdNotFoundException("존재하지 않는 id(" + id + ")");
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

    public int returnToTalElements() {
        return Optional.ofNullable(jdbcTemplate.queryForObject("select count(*) from todos", Integer.class))
                .orElse(0);
    }

    private void checkPassword(String storedPassword, String password) {
        if (!storedPassword.equals(password))
            throw new PasswordMismatchException("비밀번호 불일치");
    }

    private String getNowDatetime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        return sdf.format(now);
    }
}