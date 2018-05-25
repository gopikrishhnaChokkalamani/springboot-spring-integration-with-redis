package com.springboot.messaging.library.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.redis.outbound.RedisQueueOutboundChannelAdapter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.springboot.messaging.library.config.MessageConfig;
import com.springboot.messaging.library.mapper.MessageChannelMapper;
import com.springboot.messaging.library.service.ConsumerServiceFactory;

@Component
public class MessageListener {

	@Autowired
	private ConsumerServiceFactory serviceFactory;

	@Autowired
	private MessageChannelMapper requestChannelMapper;

	@Autowired
	private JedisConnectionFactory jedisConnectionFactory;

	// Request from Producer comes to this method and is sent to sendChannel
	@ServiceActivator(inputChannel = "gatewayInputChannel", outputChannel = "sendChannel")
	public Message<?> routeToOutBoundChannel(Message<?> message) {
		requestChannelMapper.setReplyChannels(String.valueOf(message.getHeaders().get(MessageConfig.CORRELATION_ID)),
				(MessageChannel) message.getHeaders().getReplyChannel());
		return message;
	}

	// sendChannel posts the message in redis queue for both sender and receivers
	@ServiceActivator(inputChannel = "sendChannel")
	public void sendToRedisQueue(Message<?> message) {
		RedisQueueOutboundChannelAdapter redisQueue = new RedisQueueOutboundChannelAdapter(
				(String) message.getHeaders().get(MessageConfig.ROUTING_KEY), jedisConnectionFactory);
		redisQueue.setExtractPayload(false);
		redisQueue.handleMessage(message);
	}

	// Message is posted to the redis queue and then posted to another output
	// channel receiver channel, from here the actual consumers are invoked
	@ServiceActivator(inputChannel = "receiverChannel", outputChannel = "sendChannel")
	public Message<Object> recieveRequestMsgFromAPI(Message<Object> message) {
		
		Object response = serviceFactory.getService(message.getPayload().getClass().getSimpleName()).handleServiceRequest(message.getPayload());
		Message<Object> messageBuilder = MessageBuilder.fromMessage(message)
				.setHeader(MessageConfig.ROUTING_KEY, message.getHeaders().get(MessageConfig.REPLY_QUEUE_NAME))
				.removeHeader(MessageConfig.REPLY_QUEUE_NAME).build();
		return new GenericMessage<>(response, messageBuilder.getHeaders());
	}

	// Redis reply channel
	@ServiceActivator(inputChannel = "replyChannel")
	public void recieveReplyMessageFromAdapter(Message<Object> message) {

		String corId = message.getHeaders().get(MessageConfig.CORRELATION_ID).toString();
		if (requestChannelMapper.getReplyChannels().containsKey(corId)) {
			MessageChannel reply = requestChannelMapper.getReplyChannels().get(corId);
			requestChannelMapper.getReplyChannels().remove(corId);
			reply.send(message);
		}
	}
}
