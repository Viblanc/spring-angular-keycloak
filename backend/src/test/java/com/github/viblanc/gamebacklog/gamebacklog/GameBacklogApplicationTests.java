package com.github.viblanc.gamebacklog.gamebacklog;

import com.github.viblanc.gamebacklog.gamebacklog.configuration.PostgresContainerConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
@Import(PostgresContainerConfig.class)
class GameBacklogApplicationTests {
	@Test
	void contextLoads() {
	}

	@DynamicPropertySource
	static void registerIGDBApiProperties(DynamicPropertyRegistry registry) {
		registry.add("app.igdb.clientId", () -> "clientId");
		registry.add("app.igdb.clientSecret", () -> "clientSecret");
	}
}
