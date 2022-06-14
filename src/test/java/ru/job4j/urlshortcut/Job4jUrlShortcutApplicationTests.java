package ru.job4j.urlshortcut;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql({"/schema_009.sql", "/insert_003.sql"})
class Job4jUrlShortcutApplicationTests {

    @Test
    void contextLoads() {
    }

}
