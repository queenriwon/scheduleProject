package com.example.scheduleproject.service;

import com.example.scheduleproject.dto.TodoRequestDto;
import com.example.scheduleproject.dto.TodoResponseDto;
import com.example.scheduleproject.repository.ScheduleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public TodoResponseDto createTodo(TodoRequestDto dto) {

        if (!(dto.getPassword().equals(dto.getPasswordCheck())))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password != PasswordCheck");

        return scheduleRepository.createTodo(dto);
    }

    @Override
    public List<TodoResponseDto> findTodoByNameOrUpdatedAt(String name, String updatedAtFrom, String updatedAtTo) {

        if (!updatedAtValidation(updatedAtFrom, updatedAtTo)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정일의 처음과 끝 둘 중 하나만 입력됨");
        }

        if (name != null && updatedAtFrom == null) {
            return scheduleRepository.findTodoByName(name);
        } else if (name == null && updatedAtFrom != null) {
            return scheduleRepository.findTodoByUpdatedAt(updatedAtFrom, updatedAtTo);
        } else if (name != null && updatedAtFrom != null) {
            return scheduleRepository.findTodoByNameAndUpdatedAt(name, updatedAtFrom, updatedAtTo);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정일 이나 이름이 입력되지 않았을 떄");
        }
    }

    @Override
    public List<TodoResponseDto> findTodoAll() {
        return scheduleRepository.findTodoAll();
    }

    @Override
    public TodoResponseDto findTodoById(Long id) {
        return scheduleRepository.findTodoByIdElseThrow(id);
    }

    @Override
    public TodoResponseDto updateNameAndTodo(Long id, String name, String todo, String password) {

        if (name == null || todo == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "항상 필수값임");
        }

        int updatedrows = scheduleRepository.updateNameAndTodo(id, name, todo, password);
        if (updatedrows == 0) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "찾을수 없는 id" + id);

        return scheduleRepository.findTodoByIdElseThrow(id);
    }

    private boolean updatedAtValidation(String updatedAtFrom, String updatedAtTo) {
        return !((updatedAtFrom != null) ^ (updatedAtTo != null));
    }

}
