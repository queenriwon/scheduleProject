package com.example.scheduleproject.controller;

import com.example.scheduleproject.dto.*;
import com.example.scheduleproject.exception.MissingRequestParameterException;
import com.example.scheduleproject.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ApiResponse<TodoResponseDto> createTodo(@Valid @RequestBody TodoRequestDto dto) {
        TodoResponseDto responseDto = scheduleService.createTodo(dto);
        return ApiResponse.OK("일정 등록 완료", responseDto);
    }

    @GetMapping
    public ApiResponse<PageResponseDto<TodoResponseDto>> findTodoByNameOrUpdatedAt(
            @ModelAttribute TodoRequestGetDto dto,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResponseDto<TodoResponseDto> pageResponseDto = scheduleService.findTodoByNameOrUpdatedAt(dto, page, size);
        return ApiResponse.OK("특정 조건의 일정 조회 완료", pageResponseDto);
    }

    @GetMapping("/read-all")
    public ApiResponse<PageResponseDto<TodoResponseDto>> findTodosAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResponseDto<TodoResponseDto> pageResponseDto = scheduleService.findTodoAll(page, size);
        return ApiResponse.OK("모든 일정 조회 완료", pageResponseDto);
    }

    @GetMapping("/{id}")
    public ApiResponse<TodoResponseDto> findTodoById(@PathVariable Long id) {
        TodoResponseDto responseDto = scheduleService.findTodoById(id);
        return ApiResponse.OK("아이디 " + id + "의 일정 조회 완료", responseDto);
    }

    @PatchMapping("/{id}")
    public ApiResponse<TodoResponseDto> updateNameAndTodo(
            @PathVariable Long id,
            @RequestBody TodoRequestDto dto
    ) {
        TodoResponseDto responseDto = scheduleService.updateNameAndTodo(id, dto.getName(), dto.getTodo(), dto.getPassword());
        return ApiResponse.OK("아이디 " + id + "의 일정 수정 완료", responseDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteTodoById(
            @PathVariable Long id,
            @RequestBody TodoRequestDto dto) {
        scheduleService.deleteTodoById(id, dto.getPassword());
        return ApiResponse.OK("아이디 " + id + "의 일정 삭제 완료");
    }

    @PatchMapping
    @DeleteMapping
    public ApiResponse<String> handleMissingId() {
        throw new MissingRequestParameterException("id 파라미터 값 받지 못함");
    }
}
