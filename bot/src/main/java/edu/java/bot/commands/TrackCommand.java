package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class TrackCommand implements Command {
    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return "добавляет новый ресурс для отслеживания";
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = getChatId(update);
        if (update.message().text().split(" ").length == 2) {
            if (update.message().text().split(" ")[0].equals(command())) {
                //проверка ссылки на валидность
                return new SendMessage(chatId, "Ресурс добавлен в список для отслеживания");
            }
            return new SendMessage(chatId, "Команда содержит лишние символы! Введите в формате /track {ресурс для отслеживания}");
        }
        return new SendMessage(chatId, "Введите команду в формате /track {отслеживаемый ресурс}");
    }
}
