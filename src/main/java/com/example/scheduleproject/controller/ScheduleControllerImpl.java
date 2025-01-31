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
public class ScheduleControllerImpl{

    private final ScheduleService scheduleService;

    public ScheduleControllerImpl(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ApiResponseDto<TodoResponseDto> createTodo(@Valid @RequestBody TodoRequestDto dto) {
        TodoResponseDto responseDto = scheduleService.createTodo(dto);
        return ApiResponseDto.OK("일정 등록 완료", responseDto);
    }

    @GetMapping
    public ApiResponseDto<PageResponseDto<TodoResponseDto>> findTodoByNameOrUpdatedAt(
            @ModelAttribute TodoRequestGetDto dto,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResponseDto<TodoResponseDto> pageResponseDto = scheduleService.findTodoByNameOrUpdatedAt(dto, page, size);
        return ApiResponseDto.OK("특정 조건의 일정 조회 완료", pageResponseDto);
    }

    @GetMapping("/read-all")
    public ApiResponseDto<PageResponseDto<TodoResponseDto>> findTodosAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResponseDto<TodoResponseDto> pageResponseDto = scheduleService.findTodoAll(page, size);
        return ApiResponseDto.OK("모든 일정 조회 완료", pageResponseDto);
    }

    @GetMapping("/{id}")
    public ApiResponseDto<TodoResponseDto> findTodoById(@PathVariable Long id) {
        TodoResponseDto responseDto = scheduleService.findTodoById(id);
        return ApiResponseDto.OK("아이디 " + id + "의 일정 조회 완료", responseDto);
    }

    @PatchMapping("/{id}")
    public ApiResponseDto<TodoResponseDto> updateNameAndTodo(
            @PathVariable Long id,
            @RequestBody TodoRequestDto dto
    ) {
        TodoResponseDto responseDto = scheduleService.updateNameAndTodo(id, dto.getName(), dto.getTodo(), dto.getPassword());
        return ApiResponseDto.OK("아이디 " + id + "의 일정 수정 완료", responseDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponseDto<Void> deleteTodoById(
            @PathVariable Long id,
            @RequestBody TodoRequestDto dto) {
        scheduleService.deleteTodoById(id, dto.getPassword());
        return ApiResponseDto.OK("아이디 " + id + "의 일정 삭제 완료");
    }

    @PatchMapping
    @DeleteMapping
    public ApiResponseDto<String> handleMissingId() {
        throw new MissingRequestParameterException("id 파라미터 값 받지 못함");
    }
}
