package com.example.scheduleproject.repository;

import com.example.scheduleproject.entity.UsersEntity;
import com.example.scheduleproject.mapper.ToRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class UsersRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ToRowMapper toRowMapper;

    public UsersRepository(JdbcTemplate jdbcTemplate, ToRowMapper toRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.toRowMapper = toRowMapper;
    }

    public Optional<Long> findUserIdByEmail(String email) {
        String sql = "SELECT id FROM users WHERE email = ?";

        try {
            Long userId = jdbcTemplate.queryForObject(sql, Long.class, email);
            return Optional.ofNullable(userId);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Long saveUser(String name, String email) {
        SimpleJdbcInsert jdbcInsertUser = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsertUser.withTableName("users").usingGeneratedKeyColumns("id");

        Map<String, Object> parametersUser = new HashMap<>();
        parametersUser.put("name", name);
        parametersUser.put("email", email);
        parametersUser.put("created_at", getNowDatetime());
        parametersUser.put("updated_at", getNowDatetime());

        return jdbcInsertUser.executeAndReturnKey(new MapSqlParameterSource(parametersUser)).longValue();
    }

    public List<Long> findUserIdByName(String name) {
        return jdbcTemplate.queryForList("SELECT id FROM users WHERE name = ?", Long.class, name);
    }

    public UsersEntity findNameAndEmailByUserId(Long userId) {
        return jdbcTemplate.query("SELECT * FROM users WHERE id = ?",
                toRowMapper.usersRowMapper(), userId).get(0);
    }

    public void updateUserName(Long userId, String name) {
        jdbcTemplate.update("update users set name = ? where id = ?", name, userId);
    }

    private String getNowDatetime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        return sdf.format(now);
    }
}
