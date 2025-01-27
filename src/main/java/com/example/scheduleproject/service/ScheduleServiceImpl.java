package com.example.scheduleproject.service;

import com.example.scheduleproject.dto.TodoRequestDto;
import com.example.scheduleproject.dto.TodoResponseDto;
import com.example.scheduleproject.repository.ScheduleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    @Override
    public TodoResponseDto createTodo(TodoRequestDto dto) {
        if (!(dto.getPassword().equals(dto.getPasswordCheck())))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password != PasswordCheck");



        return scheduleRepository.createTodo(dto);
    }

    @Override
    public List<TodoResponseDto> findTodoByNameOrUpdatedAt(String name, String updatedAtFrom, String updatedAtTo) {
        return scheduleRepository.findTodoByNameAndUpdatedAt(name, updatedAtFrom, updatedAtTo);
    }

    @Override
    public List<TodoResponseDto> findTodoAll() {
        return scheduleRepository.findTodoAll();
    }

    @Override
    public TodoResponseDto findTodoById(Long id) {
        return scheduleRepository.findTodoByIdElseThrow(id);
    }

    @Transactional
    @Override
    public TodoResponseDto updateNameAndTodo(Long id, String name, String todo, String password) {
        if (name == null || todo == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "항상 필수값임");
        }

        int updatedRows = scheduleRepository.updateNameAndTodo(id, name, todo, password);
        if (updatedRows == 0) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "찾을수 없는 id" + id);

        return scheduleRepository.findTodoByIdElseThrow(id);
    }

    @Transactional
    @Override
    public void deleteTodoById(Long id, String password) {
        scheduleRepository.deleteTodoById(id, password);
    }
}
