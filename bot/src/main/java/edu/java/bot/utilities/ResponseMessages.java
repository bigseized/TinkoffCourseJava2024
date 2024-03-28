package edu.java.bot.utilities;

import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ResponseMessages {
    public static final String DEFAULT_INCORRECT_COMMAND = "Команда содержит лишние символы/аргументы";
    public static final String HELP_COMMAND = "/help";
    public static final String HELP_DESCRIPTION = "выводит описание всех команд";
    public static final String LIST_COMMAND = "/list";
    public static final String LIST_DESCRIPTION = "выводит список всех отслеживаемых ресурсов";
    public static final String LIST_EMPTY = "Список ссылок для отслеживания пуст!";
    public static final String DUMMY_GITHUB = "https://github.com/bigseized";
    public static final String DUMMY_STACKOVERFLOW = "https://stackoverflow.com/questions";
    public static final String START_COMMAND = "/start";
    public static final String START_DESCRIPTION = "запускает бота";
    public static final String WELCOME_MESSAGE = """
        <b>ReminderBot</b> - ваш главный помощник в мониторинге изменений!
        Просто введите <b>/track</b> <i>{ссылка}</i> и бот сделает вашу жизнь проще!
        Для просмотра всех команд введите <b>/help</b>.
        """;
    public static final String TRACK_COMMAND = "/track";
    public static final String TRACK_DESCRIPTION = "добавляет новый ресурс для отслеживания";
    public static final String TRACK_SUCCESS = "Ресурс добавлен в список для отслеживания";
    public static final String TRACK_INCORRECT_COMMAND =
        "Команда содержит лишние символы! Введите в формате <b>/track</b> {ресурс для отслеживания}";
    public static final String TRACK_INCORRECT_FORMAT =
        "Введите команду в формате <b>/track</b> {отслеживаемый ресурс}";
    public static final String INCORRECT_LINK = "Формат ссылки не поддерживается!";

    public static final String UNTRACK_COMMAND = "/untrack";
    public static final String UNTRACK_DESCRIPTION = "удаляет ресурс из списка отслеживаемых";
    public static final String UNTRACK_SUCCESS = "Ресурс удалён из списка для отслеживания";
    public static final String UNTRACK_INCORRECT_COMMAND =
        "Команда содержит лишние символы! Введите в формате <b>/untrack</b> {отслеживаемый ресурс}";
    public static final String UNTRACK_INCORRECT_FORMAT =
        "Введите команду в формате <b>/untrack</b> {отслеживаемый ресурс}";
    public static final String UNSUPPORTED_COMMAND =
        "Не поддерживаемая команда введите <b>/help</b> для просмотра доступных команд";
    public static final List<String> DUMMY_LINKS = List.of(DUMMY_GITHUB, DUMMY_STACKOVERFLOW);
    public static final String LIST_TITLE = "Отслеживаемые ресурсы:\n";
    public static final String WELCOME_TITLE = "Привет, %s!\n";
    public static final String LINK_UPDATE_TITLE = "По одной из ваших ссылок произошло обновление!";
}
