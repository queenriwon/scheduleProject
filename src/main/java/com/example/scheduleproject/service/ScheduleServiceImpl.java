package com.example.scheduleproject.service;

import com.example.scheduleproject.dto.PageResponseDto;
import com.example.scheduleproject.dto.TodoRequestDto;
import com.example.scheduleproject.dto.TodoRequestGetDto;
import com.example.scheduleproject.dto.TodoResponseDto;
import com.example.scheduleproject.entity.TodosEntity;
import com.example.scheduleproject.entity.UsersEntity;
import com.example.scheduleproject.exception.IdNotFoundException;
import com.example.scheduleproject.exception.InvalidRequestException;
import com.example.scheduleproject.exception.NoMatchPasswordConfirmation;
import com.example.scheduleproject.mapper.TodosToMapper;
import com.example.scheduleproject.mapper.TodosToMapperImpl;
import com.example.scheduleproject.repository.TodosRepository;
import com.example.scheduleproject.repository.TodosRepositoryImpl;
import com.example.scheduleproject.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final TodosRepository scheduleRepository;
    private final UsersRepository usersRepository;
    private final TodosToMapper todosToMapper = new TodosToMapperImpl();
    private final TodosRepositoryImpl todosRepository;

    public ScheduleServiceImpl(TodosRepository scheduleRepository, UsersRepository usersRepository, TodosRepositoryImpl todosRepository) {
        this.scheduleRepository = scheduleRepository;
        this.usersRepository = usersRepository;
        this.todosRepository = todosRepository;
    }

    @Transactional
    @Override
    public TodoResponseDto createTodo(TodoRequestDto dto) {
        if (!dto.areAllNotNull())
            throw new InvalidRequestException("필수 요청 값을 받지 못함");
        if (!(dto.getPassword().equals(dto.getPasswordCheck())))
            throw new NoMatchPasswordConfirmation("비밀번호 확인 불일치");

        // userid를 불러옴 (UsersRepository 사용) (없을시 코드도 작성 + 유저 테이블에 데이터가 생성됨)
        Long userId = usersRepository.findUserIdByEmail(dto.getEmail())
                .orElseGet(() -> usersRepository.saveUser(dto.getName(), dto.getEmail()));

        // 가져온 userid를 이용해 저장 후 조회
        TodosEntity todosEntity = todosRepository.createTodo(userId, dto);

        // TodoResponseDto 생성
        return todosToMapper.toDTO(todosEntity, dto.getName(), dto.getEmail());
    }

    @Override
    public PageResponseDto<TodoResponseDto> findTodoByNameOrUpdatedAt(TodoRequestGetDto dto, int page, int size) {
        List<Long> userIdList = new ArrayList<>();

        // name을 받았다면 UsersRepository 사용하여 user name으로 user id값을 가져옴
        if (dto.getName() != null) {
            userIdList = usersRepository.findUserIdByName(dto.getName());
        }

        // user id 값과 수정일을 사용해 조회
        return scheduleRepository.findTodoByNameAndUpdatedAt(userIdList, dto.getUpdatedAtFrom(), dto.getUpdatedAtTo(), page, size);
    }

    @Override
    public PageResponseDto<TodoResponseDto> findTodoAll(int page, int size) {
        return scheduleRepository.findTodoAll(page, size);
    }

    @Override
    public TodoResponseDto findTodoById(Long id) {

        // TodosRepository 사용하여 id로 userId를 가져옴
        TodosEntity todosEntity = scheduleRepository.findTodoById(id)
                .orElseThrow(() -> new IdNotFoundException("존재하지 않는 id(" + id + ")"));

        // UsersRepository 사용하여 userId로 name과 email을 가져옴
        UsersEntity usersEntity = usersRepository.findNameAndEmailByUserId(todosEntity.getUser_id());

        // DTO = todosEntity + usersEntity
        return todosToMapper.toDTO(todosEntity, usersEntity.getName(), usersEntity.getEmail());
    }

    @Transactional
    @Override
    public TodoResponseDto updateNameAndTodo(Long id, String name, String todo, String password) {
        // 둘다 입력되지 않았을시 예외던짐
        if (name == null && todo == null) {
            throw new InvalidRequestException("필수 요청 값을 받지 못함");
        }

        // 해당 id값 일정을 찾아옴(비밀번호 확인)
        TodosEntity todosEntity = todosRepository.findTodoByIdAndAuthorize(id, password)
                .orElseThrow(() -> new IdNotFoundException("존재하지 않는 id(" + id + ")"));

        // name 입력시 UsersRepository에서 값 update(이름수정에대한 값 업데이트)
        if (name != null) {
            usersRepository.updateUserName(todosEntity.getUser_id(), name);
        }

        // todos 입력시 TodosRepository에서 값 update(일정수정에 대한 값 업데이트)
        if (todo != null) {
            todosRepository.updateTodo(id, todo);
        }

        // 업데이트한 값을 조회해서 출력
        return findTodoById(id);
    }

    @Transactional
    @Override
    public void deleteTodoById(Long id, String password) {
        scheduleRepository.deleteTodoById(id, password);
    }
}
