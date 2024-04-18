package com.github.viblanc.gamebacklog.gamebacklog;

import com.github.viblanc.gamebacklog.gamebacklog.configuration.PostgresContainerConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(PostgresContainerConfig.class)
class GameBacklogApplicationTests {
	@Test
	void contextLoads() {
	}
}
