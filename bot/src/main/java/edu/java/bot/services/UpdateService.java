package edu.java.bot.services;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.controllers.dto.request.LinkUpdateRequest;
import edu.java.bot.telegram.sender.BotTextMessageSender;
import edu.java.bot.utilities.ResponseMessages;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateService {
    private final BotTextMessageSender sender;

    public void processUpdate(LinkUpdateRequest updateRequest) {
        List<Long> chatIds = updateRequest.tgChatIds();
        String message = buildMessage(updateRequest.url(), updateRequest.description());
        for (var chatId : chatIds) {
            SendMessage sendMessage = new SendMessage(chatId, message);
            sender.sendMessage(sendMessage);
        }
    }

    private String buildMessage(URI link, String description) {
        return ResponseMessages.LINK_UPDATE_TITLE + "\n"
               + String.format("<b>Ссылка:</b> %s \n<b>Обновленный контент:</b> %s", link.toString(), description);
    }
}
