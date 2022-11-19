package com.heroes.api.heroesapi;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * This is a built in test that comes with the Spring framework that validates
 * that the REST API service starts (that's it)
 */
@Tag("Controller-tier")
@SpringBootTest
class HeroesApiApplicationTests {

	@Test
	void testContextLoads() {
	}
}
