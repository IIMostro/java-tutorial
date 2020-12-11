package org.ilmostro.start.service.condition;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * @author li.bowei
 * @date 2020/7/16 9:33
 */
@Slf4j
@Service
@ConditionalOnProperty(value = "start.active.source", havingValue = "wechat")
public class WechatServiceImpl implements ActiveService{
    @Override
    public String execute() {
        log.info("this is wechat service");
        return "this is wechat service";
    }
}
