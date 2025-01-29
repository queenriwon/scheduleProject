package com.example.scheduleproject.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PageResponseDto<T> {

    private List<T> schedulesData;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public PageResponseDto(List<T> schedulesData, int page, int size, long totalElements) {
        this.schedulesData = schedulesData;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = (int) Math.ceil((double) totalElements / size);
    }
}
