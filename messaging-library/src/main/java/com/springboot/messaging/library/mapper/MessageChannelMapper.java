package com.springboot.messaging.library.mapper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public class MessageChannelMapper {

	private Map<String, MessageChannel> replyChannels = new ConcurrentHashMap<>();

	public Map<String, MessageChannel> getReplyChannels() {
		return replyChannels;
	}

	public void setReplyChannels(String name, MessageChannel replyChannel) {
		replyChannels.put(name, replyChannel);
	}
}
