package com.example.scheduleproject.controller;

import com.example.scheduleproject.dto.TodoRequestDto;
import com.example.scheduleproject.dto.TodoResponseDto;
import com.example.scheduleproject.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.scheduleproject.dto.TodoGetRequestDto;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody TodoRequestDto dto) {
        return new ResponseEntity<>(scheduleService.createTodo(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<TodoResponseDto> findTodoByNameOrUpdatedAt(@RequestBody TodoGetRequestDto dto) {
        return scheduleService.findTodoByNameOrUpdatedAt(dto.getName(), dto.getUpdatedAtFrom(), dto.getUpdatedAtTo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> findTodoById(@PathVariable Long id) {
        return new ResponseEntity<>(scheduleService.findTodoById(id), HttpStatus.OK);
    }
}
