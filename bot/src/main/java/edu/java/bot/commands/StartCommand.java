package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command {
    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "запускает бота";
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = getChatId(update);
        if (update.message().text().equals(command())) {
            StringBuilder sendMessage = new StringBuilder();
            sendMessage.append("""
            ReminderBot - ващ главный помощник в мониторинге изменений!
            Просто введите /track {ссылка} и бот сделает вашу жизнь проще!""");
            return new SendMessage(chatId, sendMessage.toString());
        }
        return new SendMessage(chatId, "Команда содержит лишние символы/аргументы");
    }
}
