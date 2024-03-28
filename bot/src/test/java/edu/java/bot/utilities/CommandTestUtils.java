package edu.java.bot.utilities;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import edu.java.bot.clients.api.scrapper.ScrapperClient;
import edu.java.bot.clients.api.scrapper.dto.response.LinkResponse;
import edu.java.bot.clients.api.scrapper.dto.response.ListLinksResponse;
import java.net.URI;
import lombok.experimental.UtilityClass;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@UtilityClass
public class CommandTestUtils {

    private static final long ID = 10L;
    private static final String DEFAULT_NAME = "User";
    private static final String DEFAULT_LINK = "www.google.com";

    public static Update createMockUpdate(String command) {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        User user = mock(User.class);

        when(chat.id()).thenReturn(ID);
        when(message.chat()).thenReturn(chat);
        when(message.from()).thenReturn(user);
        when(user.firstName()).thenReturn(DEFAULT_NAME);
        when(message.text()).thenReturn(command);
        when(update.message()).thenReturn(message);
        return update;
    }

    public static Update createMockUpdateEditedMessage(String command) {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);

        when(chat.id()).thenReturn(ID);
        when(message.chat()).thenReturn(chat);
        when(message.text()).thenReturn(command);
        when(update.editedMessage()).thenReturn(message);
        return update;
    }

    public static ScrapperClient createMockScrapperClient() {
        ScrapperClient baseScrapper = new ScrapperClient(DEFAULT_LINK);
        ScrapperClient scrapperClient = spy(baseScrapper);
        when(scrapperClient.deleteLinks(anyLong(), any())).thenReturn(new LinkResponse(1, URI.create(DEFAULT_LINK)));
        when(scrapperClient.postLinks(anyLong(), any())).thenReturn(new LinkResponse(1, URI.create(DEFAULT_LINK)));
        when(scrapperClient.getLinks(anyLong())).thenReturn(new ListLinksResponse(null, 0));
        doNothing().when(scrapperClient).postTelegramChat(anyLong());
        doNothing().when(scrapperClient).deleteTelegramChat(anyLong());
        return scrapperClient;
    }
}
