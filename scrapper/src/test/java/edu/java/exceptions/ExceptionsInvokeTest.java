package edu.java.exceptions;

import edu.java.controllers.TelegramChatsController;
import edu.java.controllers.TelegramLinksController;
import edu.java.controllers.dto.request.AddLinkRequest;
import edu.java.controllers.dto.request.RemoveLinkRequest;
import edu.java.services.link.LinkService;
import edu.java.services.chat.TgChatService;
import java.net.URI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class ExceptionsInvokeTest {
    private static final TgChatService chatService = mock(TgChatService.class);
    private static final LinkService linkService = mock(LinkService.class);
    private static final TelegramChatsController telegramChatsController =
        new TelegramChatsController(chatService);
    private static final TelegramLinksController telegramLinksController =
        new TelegramLinksController(linkService);
    public static final URI LINK = URI.create("test.com");

    @Test
    @DisplayName("ChatAlreadyRegistered invoke test")
    public void chatAlreadyRegisteredInvoke() {
        doThrow(ChatAlreadyRegisteredException.class).when(chatService).register(409L);
        assertThatThrownBy(() -> telegramChatsController.postTgChat(409L)).isInstanceOf(ChatAlreadyRegisteredException.class);
    }

    @Test
    @DisplayName("ChatNotFound invoke test")
    public void chatNotFoundInvoke() {
        doThrow(ChatNotFoundException.class).when(chatService).unregister(404L);
        assertThatThrownBy(() -> telegramChatsController.deleteTgChat(404L)).isInstanceOf(ChatNotFoundException.class);
    }

    @Test
    @DisplayName("LinkAlreadyRegistered invoke test")
    public void linkAlreadyRegisteredInvoke() {
        doThrow(LinkAlreadyRegisteredException.class)
            .when(linkService)
            .add(409L, LINK);
        assertThatThrownBy(() -> telegramLinksController.postLink(409L, new AddLinkRequest(LINK)))
            .isInstanceOf(LinkAlreadyRegisteredException.class);
    }

    @Test
    @DisplayName("LinkFormatUnsupported invoke test")
    public void linkFormatUnsupportedException() {
        doThrow(LinkFormatUnsupportedException.class)
            .when(linkService)
            .add(400L, LINK);
        assertThatThrownBy(() -> telegramLinksController.postLink(400L, new AddLinkRequest(LINK)))
            .isInstanceOf(LinkFormatUnsupportedException.class);
    }


    @Test
    @DisplayName("LinkNotFound invoke test")
    public void linkNotFoundInvoke() {
        doThrow(LinkNotFoundException.class)
            .when(linkService)
            .remove(404L, LINK);
        assertThatThrownBy(() -> telegramLinksController.deleteLink(404L, new RemoveLinkRequest(LINK)))
            .isInstanceOf(LinkNotFoundException.class);
    }

}
