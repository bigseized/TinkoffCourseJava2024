package edu.java.bot.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface Command {
    String command();

    String description();

    SendMessage handle(Update update);

    default boolean supports(Update update) {
        return update.message().text().startsWith(command());
    }

    default long getChatId(Update update) {
        return update.message().chat().id();
    }

    default BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }
}
