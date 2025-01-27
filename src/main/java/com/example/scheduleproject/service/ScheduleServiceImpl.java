package com.example.scheduleproject.service;

import com.example.scheduleproject.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }
}
