package com.example.scheduleproject.service;

import com.example.scheduleproject.dto.TodoRequestDto;
import com.example.scheduleproject.dto.TodoResponseDto;
import com.example.scheduleproject.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public TodoResponseDto createTodo(TodoRequestDto dto) {
        if (!dto.getPassword().equals(dto.getPasswordCheck()))
            new ResponseStatusException(HttpStatus.BAD_REQUEST,"Password != PasswordCheck");
        return scheduleRepository.createTodo(dto);
    }

}
