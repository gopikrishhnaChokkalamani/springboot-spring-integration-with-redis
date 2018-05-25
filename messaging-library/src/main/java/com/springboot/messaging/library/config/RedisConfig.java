package com.springboot.messaging.library.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.ExecutorChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.redis.inbound.RedisQueueMessageDrivenEndpoint;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.springboot.messaging.library.condition.VerifyConsumerCondition;
import com.springboot.messaging.library.condition.VerifyProducerCondition;

@Configuration
@EnableIntegration
@IntegrationComponentScan("com.springboot.messaging.library")
public class RedisConfig {

	@Value("${message.library.channel.redis.queue}")
	private String instanceQueue;

	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setHostName("localhost");
		factory.setPort(6379);
		return factory;
	}

	@Bean
	@Conditional(VerifyConsumerCondition.class)
	//Conditional used to create receiver queue
	public RedisQueueMessageDrivenEndpoint consumerListener() {
		RedisQueueMessageDrivenEndpoint endPoint = new RedisQueueMessageDrivenEndpoint(instanceQueue,
				jedisConnectionFactory());
		endPoint.setExpectMessage(true);
		endPoint.setOutputChannelName("receiverChannel");
		return endPoint;
	}

	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(100);
		executor.setMaxPoolSize(500);
		executor.setQueueCapacity(100);
		return executor;
	}

	@Bean
	@Conditional(VerifyProducerCondition.class)
	//Conditional used to create reply queue
	public RedisQueueMessageDrivenEndpoint producerListener() {
		RedisQueueMessageDrivenEndpoint endPoint = new RedisQueueMessageDrivenEndpoint(MessageConfig.REPLY_QUEUE_VALUE,
				jedisConnectionFactory());
		endPoint.setExpectMessage(true);
		endPoint.setOutputChannelName("replyChannel");
		return endPoint;
	}

	@Bean
	public ExecutorChannel receiverChannel() {
		return new ExecutorChannel(taskExecutor());
	}

	@Bean
	ExecutorChannel replyChannel() {
		return new ExecutorChannel(taskExecutor());
	}
}
