package com.idsargus.akpmsadminservice.batchutilities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class BatchConfigurationThreadpool {
	@Bean(name = "batchTaskExecutor")
	public ThreadPoolTaskExecutor apiTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(20);
		executor.setMaxPoolSize(100);
		executor.setQueueCapacity(50);
		executor.initialize();
		return executor;
	}
}
