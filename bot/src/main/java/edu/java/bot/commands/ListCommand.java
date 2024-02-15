package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class ListCommand implements Command {
    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "выводит список всех отслеживаемых ресурсов";
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = getChatId(update);
        if (update.message().text().equals(command())) {
            StringBuilder sendMessage = new StringBuilder();
            sendMessage.append("https://github.com/bigseized\n");
            sendMessage.append("https://stackoverflow.com/questions");
            return new SendMessage(chatId, sendMessage.toString());
        }
        return new SendMessage(chatId, "Команда содержит лишние символы/аргументы");
    }
}
