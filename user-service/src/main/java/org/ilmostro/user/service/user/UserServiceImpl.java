package org.ilmostro.user.service.user;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.user.domain.department.Department;
import org.ilmostro.user.domain.properties.CustomRedisProperties;
import org.ilmostro.user.domain.user.User;
import org.ilmostro.user.enums.basic.MessageFlag;
import org.ilmostro.user.repository.department.DepartmentRepository;
import org.ilmostro.user.repository.user.UserRepository;
import org.ilmostro.user.stream.*;
import org.ilmostro.user.utils.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author li.bowei on 2019-10-29.
 * @description
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMessageProducer producer;
    private final DepartmentRepository dRepository;
    private final MessageService messageService;
    private final CustomRedisProperties properties;
    private final CacheUtils<User> cache;

    @Autowired
    public UserServiceImpl(UserRepository repository,
                           UserMessageProducer producer,
                           DepartmentRepository dRepository,
                           MessageService messageService,
                           CustomRedisProperties properties,
                           CacheUtils<User> cache) {
        this.repository = repository;
        this.producer = producer;
        this.dRepository = dRepository;
        this.messageService = messageService;
        this.properties = properties;
        this.cache = cache;
    }

    @Override
    public User save(User user) {
        Department department = dRepository.findById(user.getDepartment().getId()).orElseThrow(RuntimeException::new);
        User var1 = repository.save(user);
        var1.setDepartment(department);
//        template.opsForValue().set(properties.getUser().getKey()+var1.getId().toString(), var1);
        cache.set(properties.getUser(), var1);
        messageService.send(var1 ,MessageFlag.SAVE, producer);
        return var1;
    }


    @Override
    public Page<User> queryAll(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    @Override
    public User detail(Long uid) {
        User var1 = Optional.of(uid)
                .map(Objects::toString)
                .map(var2 -> properties.getUser().getKey() + var2)
                .map(var2 -> cache.get(properties.getUser(), var2))
                .orElseGet(() -> repository.findById(uid).orElse(null));
        if(Objects.isNull(var1)){
            return null;
        }
        cache.set(properties.getUser(), var1);
        return var1;
    }

    @Override
    public User delete(Long uid) {
        User user = repository.findById(uid).orElseThrow(RuntimeException::new);
        repository.delete(user);
        messageService.send(user, MessageFlag.DELETE, producer);
        return user;
    }

    @Override
    public Page<User> queryByDepartment(Long department, Integer page, Integer size) {
        PageRequest of = PageRequest.of(page, size);
        return repository.queryUsersByDepartment_Id(department, of);
    }

    @StreamListener(MessageReceive.USER_INFO_RECEIVE)
    public void receive(CustomMessage message) {
        log.info("receive message {}", message);
    }

    @StreamListener(MessageReceive.LOG_INFO_RECEIVE)
    public void logReceive(CustomMessage message){
        log.info("receive log {}", message);
    }

    @StreamListener(MessageReceive.LOG_ERROR_RECEIVE)
    public void logErrorReceive(CustomMessage message){
        log.info("receive message {}", message);
    }

}
