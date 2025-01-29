package com.example.scheduleproject.service;

import com.example.scheduleproject.dto.TodoRequestDto;
import com.example.scheduleproject.dto.TodoResponseDto;
import com.example.scheduleproject.entity.TodosEntity;
import com.example.scheduleproject.entity.UsersEntity;
import com.example.scheduleproject.mapper.TodosToMapper;
import com.example.scheduleproject.mapper.TodosToMapperImpl;
import com.example.scheduleproject.repository.ScheduleRepository;
import com.example.scheduleproject.repository.TodosRepository;
import com.example.scheduleproject.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UsersRepository usersRepository;
    private final TodosToMapper todosToMapper = new TodosToMapperImpl();
    private final TodosRepository todosRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, UsersRepository usersRepository, TodosRepository todosRepository) {
        this.scheduleRepository = scheduleRepository;
        this.usersRepository = usersRepository;
        this.todosRepository = todosRepository;
    }

    @Transactional
    @Override
    public TodoResponseDto createTodo(TodoRequestDto dto) {
        if (!(dto.getPassword().equals(dto.getPasswordCheck())))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password != PasswordCheck");

        // userid를 불러옴 (UsersRepository 사용) (없을시 코드도 작성 + 유저 테이블에 데이터가 생성됨)
        Long userId = usersRepository.findUserIdByEmail(dto.getEmail())
                .orElseGet(() -> usersRepository.saveUser(dto.getName(), dto.getEmail()));

        // 가져온 userid를 이용해 저장 후 조회
        TodosEntity todosEntity = todosRepository.createTodo(userId, dto);

        // TodoResponseDto 생성
        return todosToMapper.toDTO(todosEntity, dto.getName(), dto.getEmail());
    }

    @Override
    public List<TodoResponseDto> findTodoByNameOrUpdatedAt(String name, String updatedAtFrom, String updatedAtTo) {
        List<Long> userIdList = new ArrayList<>();

        // name을 받았다면 UsersRepository 사용하여 user name으로 user id값을 가져옴
        if (name != null) {
            userIdList = usersRepository.findUserIdByName(name);
        }

        // user id 값과 수정일을 사용해 조회
        return scheduleRepository.findTodoByNameAndUpdatedAt(userIdList, updatedAtFrom, updatedAtTo);
    }

    @Override
    public List<TodoResponseDto> findTodoAll() {
        return scheduleRepository.findTodoAll();
    }

    @Override
    public TodoResponseDto findTodoById(Long id) {

        // TodosRepository 사용하여 id로 userId를 가져옴
        TodosEntity todosEntity = scheduleRepository.findTodoById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found id"));

        // UsersRepository 사용하여 userId로 name과 email을 가져옴
        UsersEntity usersEntity = usersRepository.findNameAndEmailByUserId(todosEntity.getUser_id());

        // DTO = todosEntity + usersEntity
        return todosToMapper.toDTO(todosEntity, usersEntity.getName(), usersEntity.getEmail());
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
