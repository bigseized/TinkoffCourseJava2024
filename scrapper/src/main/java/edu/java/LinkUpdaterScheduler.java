package edu.java;

import edu.java.services.updater.LinkUpdaterSevice;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
@EnableScheduling
public class LinkUpdaterScheduler {
    private final LinkUpdaterSevice linkUpdaterSevice;

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        linkUpdaterSevice.update();
    }
}
