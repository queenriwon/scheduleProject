package com.example.scheduleproject.controller;

import com.example.scheduleproject.dto.TodoRequestDto;
import com.example.scheduleproject.dto.TodoResponseDto;
import com.example.scheduleproject.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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
    public List<TodoResponseDto> findTodoByNameOrUpdatedAt(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String updatedAtFrom,
            @RequestParam(required = false) String updatedAtTo
    ) {
        return scheduleService.findTodoByNameOrUpdatedAt(name, updatedAtFrom, updatedAtTo);
    }

    @GetMapping("/read-all")
    public List<TodoResponseDto> findTodosAll() {
        return scheduleService.findTodoAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> findTodoById(@PathVariable Long id) {
        return new ResponseEntity<>(scheduleService.findTodoById(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TodoResponseDto> updateNameAndTodo(
            @PathVariable Long id,
            @RequestBody TodoRequestDto dto
    ) {
        return new ResponseEntity<>(scheduleService.updateNameAndTodo(id, dto.getName(), dto.getTodo(), dto.getPassword()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoById(
            @PathVariable Long id,
            @RequestBody TodoRequestDto dto) {
        scheduleService.deleteTodoById(id, dto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
