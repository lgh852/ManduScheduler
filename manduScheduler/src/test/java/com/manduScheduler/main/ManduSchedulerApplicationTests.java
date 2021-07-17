package com.manduScheduler.main;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ManduSchedulerApplicationTests {
	@Value("${jdbc.deriver}")
	private static String jdbcDeriver;
	@Value("${jdbc.url}")
	private static String jdbcUrl;
	@Value("${jdbc.user.name}")
	private static String jdbcUserName;
	@Value("${jdbc.user.password}")
	private static String jdbcPasswrod;
	@Test
	void contextLoads() {
		System.out.println(jdbcDeriver);
	}

}
