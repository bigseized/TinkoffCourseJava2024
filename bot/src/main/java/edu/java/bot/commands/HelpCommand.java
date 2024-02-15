package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HelpCommand implements Command {

    private final List<Command> commands;

    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "выводит описание всех команд";
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = getChatId(update);
        if (update.message().text().equals(command())) {
            StringBuilder sendMessage = new StringBuilder();
            for (var command : commands) {
                sendMessage.append(command.command()).append(" - ").append(command.description()).append("\n");
            }
            return new SendMessage(chatId, sendMessage.toString());
        }
        return new SendMessage(chatId, "Команда содержит лишние символы/аргументы");
    }
}
