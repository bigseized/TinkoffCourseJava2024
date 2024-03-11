package edu.java.bot.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import edu.java.bot.telegram.menu.CommandsMenu;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BotLinkTracker {
    private final CommandsMenu commandsMenu;
    private final UpdatesListener updatesListener;
    private final TelegramBot telegramBot;

    @PostConstruct
    public void start() {
        telegramBot.setUpdatesListener(updatesListener);
        telegramBot.execute(commandsMenu.getCommandsMenu());
    }
}
