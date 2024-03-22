package edu.java.integration;

import edu.java.scrapper.IntegrationEnvironment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class InitMigrationsTest extends IntegrationEnvironment {
    private final static String sql =
        "SELECT column_name FROM information_schema.columns WHERE table_name = ?;";
    private final static List<String> LINK_COLUMNS = List.of("id", "updated_at", "resource");
    private final static List<String> CHAT_COLUMNS = List.of("id");
    private final static List<String> ASSOCIATION_COLUMNS = List.of("link_id", "chat_id");

    @Test
    @DisplayName("Проверка создания таблицы Link")
    @SneakyThrows
    public void tableLinkCreateTest() {
        @Cleanup
        Connection connection = POSTGRES.createConnection("");
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "link");
        var result = statement.executeQuery();
        List<String> values = new ArrayList<>();
        while (result.next()) {
            values.add(result.getString("column_name"));
        }
        assertThat(values).containsExactlyElementsOf(LINK_COLUMNS);
    }

    @Test
    @DisplayName("Проверка создания таблицы Chat")
    @SneakyThrows
    public void tableChatCreateTest() {
        @Cleanup
        Connection connection = POSTGRES.createConnection("");
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "chat");
        var result = statement.executeQuery();
        List<String> values = new ArrayList<>();
        while (result.next()) {
            values.add(result.getString("column_name"));
        }
        assertThat(values).containsExactlyElementsOf(CHAT_COLUMNS);
    }

    @Test
    @DisplayName("Проверка создания таблицы chat_link_association")
    @SneakyThrows
    public void tableAssociationCreateTest() {
        @Cleanup
        Connection connection = POSTGRES.createConnection("");
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "chat_link_association");
        var result = statement.executeQuery();
        List<String> values = new ArrayList<>();
        while (result.next()) {
            values.add(result.getString("column_name"));
        }
        assertThat(values).containsExactlyElementsOf(ASSOCIATION_COLUMNS);
    }
}
