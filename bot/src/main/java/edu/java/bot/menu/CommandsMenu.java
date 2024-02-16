package edu.java.bot.menu;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.commands.Command;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandsMenu {
    private final List<Command> commandList;

    public SetMyCommands getCommandsMenu() {
        List<BotCommand> botCommands = commandList.stream().map(Command::toApiCommand).toList();
        return new SetMyCommands(botCommands.toArray(new BotCommand[0]));
    }
}
