package ru.otus.spring.finalproject.easydesk;

import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.finalproject.easydesk.config.AppConfig;

@SpringBootTest
@EnableConfigurationProperties(AppConfig.class)
class EasydeskApplicationTests {

    @Test
    void contextLoads() {
    }

}
