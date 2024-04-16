package com.github.viblanc.gamebacklog.gamebacklog;

import com.github.viblanc.gamebacklog.gamebacklog.configuration.TestContainersConfiguration;
import com.github.viblanc.gamebacklog.gamebacklog.service.IGDBApiService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestContainersConfiguration.class)
class GameBacklogApplicationTests {
	@MockBean
	private IGDBApiService igdbApiService;

	@Test
	void contextLoads() {
	}
}
