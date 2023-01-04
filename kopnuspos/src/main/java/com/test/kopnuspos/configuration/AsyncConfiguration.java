package com.test.kopnuspos.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.test.kopnuspos.exceptions.RejectExecutionHandlerImpl;

@Configuration
@EnableAsync
public class AsyncConfiguration {
	
	@Bean("threadPoolTaskExecutor")
	public TaskExecutor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(10);
		executor.setThreadNamePrefix("Executor:");
		executor.setRejectedExecutionHandler(new RejectExecutionHandlerImpl());
		executor.initialize();
		return executor;
	}
}
