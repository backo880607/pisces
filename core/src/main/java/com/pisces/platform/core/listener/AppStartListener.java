package com.pisces.platform.core.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

/**
 * @ClassName AppStartListener
 * @Description TODO
 * @Author Jason
 * @Date 2020-02-07
 * @ModifyDate 2020-02-07
 */
@Component
public class AppStartListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        int num = 0;
    }
}
