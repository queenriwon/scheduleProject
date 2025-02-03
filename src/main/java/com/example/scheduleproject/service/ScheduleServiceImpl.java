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
import com.example.scheduleproject.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final UsersRepository usersRepository;
    private final TodosRepository todosRepository;
    private final TodosToMapper todosToMapper = new TodosToMapperImpl();

    public ScheduleServiceImpl(UsersRepository usersRepository, TodosRepository todosRepository) {
        this.usersRepository = usersRepository;
        this.todosRepository = todosRepository;
    }

    @Transactional
    @Override
    public TodoResponseDto createTodo(TodoRequestDto dto) {
        if (!dto.areAllNotNull()){
            throw new InvalidRequestException("필수 요청 값을 받지 못함");
        }
        if (!(dto.getPassword().equals(dto.getPasswordCheck()))) {
            throw new NoMatchPasswordConfirmation("비밀번호 확인 불일치");
        }

        // 1. UsersRepository에서 userid를 불러옴 (신규 유저일시 users 생성)
        Long userId = usersRepository.findUserIdByEmail(dto.getEmail())
                .orElseGet(() -> usersRepository.saveUser(dto.getName(), dto.getEmail()));

        // 2. 가져온 userid를 이용해 일정 저장
        TodosEntity todosEntity = todosRepository.createTodo(userId, dto);

        // 3. TodoResponseDto 매핑
        return todosToMapper.toDTO(todosEntity, dto.getName(), dto.getEmail());
    }

    @Override
    public PageResponseDto<TodoResponseDto> findTodoByNameOrUpdatedAt(TodoRequestGetDto dto, int page, int size) {
        List<Long> userIdList = new ArrayList<>();

        // 1. name를 입력받을 경우 UsersRepository 사용하여 user name으로 user id값을 가져옴
        if (dto.getName() != null) {
            userIdList = usersRepository.findUserIdByName(dto.getName());
        }

        // 2. user id 값과 수정일을 사용해 조회
        return todosRepository.findTodoByNameAndUpdatedAt(userIdList, dto.getUpdatedAtFrom(), dto.getUpdatedAtTo(), page, size);
    }

    @Override
    public PageResponseDto<TodoResponseDto> findTodoAll(int page, int size) {
        return todosRepository.findTodoAll(page, size);
    }

    @Override
    public TodoResponseDto findTodoById(Long id) {

        // 1. TodosRepository 사용하여 id로 userId를 가져옴
        TodosEntity todosEntity = todosRepository.findTodoById(id)
                .orElseThrow(() -> new IdNotFoundException("존재하지 않는 id(" + id + ")"));

        // 2. UsersRepository 사용하여 userId로 name과 email을 가져옴
        UsersEntity usersEntity = usersRepository.findNameAndEmailByUserId(todosEntity.getUserId());

        // 3. ResponseDTO = todosEntity + usersEntity
        return todosToMapper.toDTO(todosEntity, usersEntity.getName(), usersEntity.getEmail());
    }

    @Transactional
    @Override
    public TodoResponseDto updateNameAndTodo(Long id, String name, String todo, String password) {
        // 1. 둘다 입력되지 않았을시 예외던짐
        if (name == null && todo == null) {
            throw new InvalidRequestException("필수 요청 값을 받지 못함");
        }

        // 2. todos테이블에서 해당 id값 일정을 찾아옴(비밀번호 확인)
        TodosEntity todosEntity = todosRepository.findTodoByIdAndAuthorize(id, password)
                .orElseThrow(() -> new IdNotFoundException("존재하지 않는 id(" + id + ")"));

        // 3. name 입력시 UsersRepository에서 값 update(이름 수정에 대한 값 업데이트)
        if (name != null) {
            usersRepository.updateUserName(todosEntity.getUserId(), name);
        }

        // 4. todos 입력시 TodosRepository에서 값 update(일정 수정에 대한 값 업데이트)
        if (todo != null) {
            todosRepository.updateTodo(id, todo);
        }

        // 5. 업데이트한 값을 조회해서 출력
        return findTodoById(id);
    }

    @Transactional
    @Override
    public void deleteTodoById(Long id, String password) {
        todosRepository.deleteTodoById(id, password);
    }
}
