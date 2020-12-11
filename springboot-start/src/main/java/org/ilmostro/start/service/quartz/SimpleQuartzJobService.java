package org.ilmostro.start.service.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

/**
 * @author li.bowei
 * @date 2020/12/11 13:35
 */
@Service
@Slf4j
public class SimpleQuartzJobService extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("start job time:{}", System.currentTimeMillis());
    }
}
