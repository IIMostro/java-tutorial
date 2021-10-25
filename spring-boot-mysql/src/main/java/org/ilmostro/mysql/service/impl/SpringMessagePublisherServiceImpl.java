package org.ilmostro.mysql.service.impl;

import org.ilmostro.mysql.entity.User;
import org.ilmostro.mysql.repository.UserRepository;
import org.ilmostro.mysql.service.MessagePublisherService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author li.bowei
 */
@Component
public class SpringMessagePublisherServiceImpl implements MessagePublisherService {

    private final ApplicationContext context;
    private final UserRepository repository;

    public SpringMessagePublisherServiceImpl(ApplicationContext context, UserRepository repository) {
        this.context = context;
        this.repository = repository;
    }

    @Override
    @Transactional
    public void publisher(User user) {
        repository.save(user);
        context.publishEvent(new MessageApplicationEvent(this, user.toString()));
    }

    public static class MessageApplicationEvent extends ApplicationEvent {

        private final String body;

        public MessageApplicationEvent(Object source, String body) {
            super(source);
            this.body = body;
        }

        public String getBody() {
            return body;
        }
    }
}
