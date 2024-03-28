package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.clients.api.scrapper.ScrapperClient;
import edu.java.bot.clients.api.scrapper.dto.response.LinkResponse;
import edu.java.bot.clients.api.scrapper.dto.response.ListLinksResponse;
import java.net.URI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.java.bot.utilities.CommandTestUtils.createMockUpdate;
import static edu.java.bot.utilities.ResponseMessages.DEFAULT_INCORRECT_COMMAND;
import static edu.java.bot.utilities.ResponseMessages.DUMMY_LINKS;
import static edu.java.bot.utilities.ResponseMessages.LIST_COMMAND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

//так как команда list пока возвращает определённый набор ссылок, тест придётся изменить
class ListCommandTest {
    ScrapperClient scrapperClient = mock(ScrapperClient.class);

    public static final String correctResponse =
        """
            Отслеживаемые ресурсы:
            <b>1</b>: https://github.com/bigseized
            <b>2</b>: https://stackoverflow.com/questions""";

    @Test
    @DisplayName("Correct /list command test")
    public void listCommand_correctCommand_test() {
        Update mockUpdate = createMockUpdate(LIST_COMMAND);
        Command command = new ListCommand(scrapperClient);
        doReturn(new ListLinksResponse(DUMMY_LINKS.stream()
            .map(link -> new LinkResponse(1, URI.create(link)))
            .toList(), 2)).when(
            scrapperClient).getLinks(anyLong());
        SendMessage message = command.handle(mockUpdate);
        assertThat(message.getParameters().get("text")).isEqualTo(correctResponse);
    }

    @Test
    @DisplayName("Incorrect /list command test")
    public void listCommand_incorrectCommand_test() {
        Update mockUpdate = createMockUpdate(LIST_COMMAND + "3");
        Command command = new ListCommand(scrapperClient);
        SendMessage message = command.handle(mockUpdate);
        assertThat(message.getParameters().get("text")).isEqualTo(DEFAULT_INCORRECT_COMMAND);
    }
}
