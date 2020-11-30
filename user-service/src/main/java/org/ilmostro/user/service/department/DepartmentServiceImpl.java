package org.ilmostro.user.service.department;

import org.ilmostro.user.domain.department.Department;
import org.ilmostro.user.domain.properties.CustomRedisProperties;
import org.ilmostro.user.enums.basic.MessageFlag;
import org.ilmostro.user.repository.department.DepartmentRepository;
import org.ilmostro.user.stream.DepartmentMessageProducer;
import org.ilmostro.user.stream.MessageService;
import org.ilmostro.user.utils.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * @author IlMostro
 * @date 2019-11-05 1:48
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository repository;
    private final DepartmentMessageProducer producer;
    private final MessageService messageService;
    private final CustomRedisProperties properties;
    private final CacheUtils<Department> cache;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository repository,
                                 DepartmentMessageProducer producer,
                                 MessageService messageService,
                                 CustomRedisProperties properties,
                                 CacheUtils<Department> cache) {
        this.repository = repository;
        this.producer = producer;
        this.messageService = messageService;
        this.properties = properties;
        this.cache = cache;
    }

    @Override
    public Department save(Department department) {
        Department save = repository.save(department);
        cache.set(properties.getDepartment(), save);
        messageService.send(save, MessageFlag.SAVE, producer);
        return save;
    }

    @Override
    public Page<Department> queryAll(Integer page, Integer size) {
        PageRequest pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

    @Override
    public Department detail(Long id) {
        Department department = Optional.of(id)
                .map(Objects::toString)
                .map(var1 -> cache.get(properties.getDepartment(), var1))
                .orElseGet(() -> repository.findById(id).orElse(null));
        if(Objects.isNull(department)){
            return null;
        }
        cache.set(properties.getDepartment(), department);
        return department;
    }

    @Override
    public Department delete(Long id) {
        return null;
    }
}
