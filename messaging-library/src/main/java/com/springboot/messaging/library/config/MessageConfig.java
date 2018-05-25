package com.springboot.messaging.library.config;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.gateway.MethodArgsHolder;
import org.springframework.integration.gateway.MethodArgsMessageMapper;
import org.springframework.integration.support.MutableMessageHeaders;
import org.springframework.messaging.Message;

import com.springboot.messaging.library.service.ConsumerServiceFactory;

@Configuration
public class MessageConfig {

	public static final String CORRELATION_ID = "correlation.id";
	public static final String ROUTING_KEY = "routing.key";
	public static final String REPLY_QUEUE_NAME = "reply.queue.name";
	public static final String REPLY_QUEUE_VALUE = UUID.randomUUID().toString();

	@Bean
	public MethodArgsMessageMapper setMessageHeaders() {
		return new MethodArgsMessageMapper() {
			@Override
			public Message<?> toMessage(MethodArgsHolder object) throws Exception {
				MutableMessageHeaders headers = new MutableMessageHeaders(new HashMap<>());
				headers.put(CORRELATION_ID, UUID.randomUUID().toString());
				headers.put(ROUTING_KEY, object.getArgs()[1]);
				headers.put(REPLY_QUEUE_NAME, REPLY_QUEUE_VALUE);
				return org.springframework.messaging.support.MessageBuilder.createMessage(object.getArgs()[0], headers);
			}
		};
	}
	
	@Bean
	public FactoryBean<?> factoryBean() {
		ServiceLocatorFactoryBean factory = new ServiceLocatorFactoryBean();
		factory.setServiceLocatorInterface(ConsumerServiceFactory.class);
		return factory;
	}
}
