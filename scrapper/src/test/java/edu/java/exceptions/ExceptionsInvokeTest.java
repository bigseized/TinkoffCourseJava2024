package edu.java.exceptions;

import edu.java.controllers.TelegramChatsController;
import edu.java.controllers.TelegramLinksController;
import edu.java.services.chat.AddTelegramChatService;
import edu.java.services.chat.DeleteTelegramChatService;
import edu.java.services.link.AddLinkService;
import edu.java.services.link.DeleteLinkService;
import edu.java.services.link.GetAllTrackedLinksService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class ExceptionsInvokeTest {
    private static final AddTelegramChatService addTelegramChatService = mock(AddTelegramChatService.class);
    private static final DeleteTelegramChatService deleteTelegramChatService = mock(DeleteTelegramChatService.class);
    private static final AddLinkService addLinkService = mock(AddLinkService.class);
    private static final DeleteLinkService deleteLinkService = mock(DeleteLinkService.class);
    private static final GetAllTrackedLinksService getAllTrackedLinksService = mock(GetAllTrackedLinksService.class);
    private static final TelegramChatsController telegramChatsController =
        new TelegramChatsController(deleteTelegramChatService, addTelegramChatService);
    private static final TelegramLinksController telegramLinksController =
        new TelegramLinksController(getAllTrackedLinksService, addLinkService, deleteLinkService);

    @Test
    @DisplayName("ChatAlreadyRegistered invoke test")
    public void chatAlreadyRegisteredInvoke() {
        doThrow(ChatAlreadyRegisteredException.class).when(addTelegramChatService).addChat(409L);
        assertThatThrownBy(() -> telegramChatsController.postTgChat(409L)).isInstanceOf(ChatAlreadyRegisteredException.class);
    }

    @Test
    @DisplayName("ChatNotFound invoke test")
    public void chatNotFoundInvoke() {
        doThrow(ChatNotFoundException.class).when(deleteTelegramChatService).deleteChat(404L);
        assertThatThrownBy(() -> telegramChatsController.deleteTgChat(404L)).isInstanceOf(ChatNotFoundException.class);
    }

    @Test
    @DisplayName("LinkAlreadyRegistered invoke test")
    public void linkAlreadyRegisteredInvoke() {
        doThrow(LinkAlreadyRegisteredException.class)
            .when(addLinkService)
            .addLink(409L, null);
        assertThatThrownBy(() -> telegramLinksController.postLink(409L, null))
            .isInstanceOf(LinkAlreadyRegisteredException.class);
    }

    @Test
    @DisplayName("LinkNotFound invoke test")
    public void linkNotFoundInvoke() {
        doThrow(LinkNotFoundException.class)
            .when(deleteLinkService)
            .deleteLink(404L, null);
        assertThatThrownBy(() -> telegramLinksController.deleteLink(404L, null))
            .isInstanceOf(LinkNotFoundException.class);
    }

}
