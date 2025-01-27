package com.example.scheduleproject.service;

import com.example.scheduleproject.dto.TodoRequestDto;
import com.example.scheduleproject.dto.TodoResponseDto;
import com.example.scheduleproject.repository.ScheduleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Slf4j
@Service
public class ScheduleServiceImpl implements ScheduleService{

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public TodoResponseDto createTodo(TodoRequestDto dto) {

        log.info("key1Value={}, key2Value={}", dto.getPassword(), dto.getPasswordCheck());
        if (!(dto.getPassword().equals(dto.getPasswordCheck())))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Password != PasswordCheck");


        return scheduleRepository.createTodo(dto);
    }

}
