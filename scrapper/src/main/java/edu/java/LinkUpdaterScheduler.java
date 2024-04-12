package edu.java;

import edu.java.services.updater.LinkUpdaterSevice;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@EnableScheduling
@ConditionalOnProperty(prefix = "app.scheduler", name = "enable", havingValue = "true")
public class LinkUpdaterScheduler {
    private final LinkUpdaterSevice linkUpdaterSevice;

    @Scheduled(fixedDelayString = "#{@scheduler.interval()}")
    public void update() {
        linkUpdaterSevice.update();
    }
}
