package com.springboot.messaging.library.intfc;

import java.util.concurrent.Future;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(mapper = "setMessageHeaders", asyncExecutor = "gatewayExecutor")
public interface MessageGateway {
	@Gateway(requestChannel = "gatewayInputChannel")
	public <S, T> Future<T> sendMessage(S request, String routingKey);
}
