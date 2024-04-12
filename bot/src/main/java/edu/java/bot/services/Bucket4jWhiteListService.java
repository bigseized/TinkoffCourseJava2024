package edu.java.bot.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(prefix = "bucket4j", name = "enabled", havingValue = "true")
public class Bucket4jWhiteListService {
    @Value("${clients-whitelist}")
    private List<String> clientsWhitelist;

    public boolean isWhiteList(String ipAddress) {
        return clientsWhitelist.contains(ipAddress);
    }
}
