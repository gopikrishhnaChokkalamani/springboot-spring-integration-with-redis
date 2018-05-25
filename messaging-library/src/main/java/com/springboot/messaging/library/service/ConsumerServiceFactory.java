package com.springboot.messaging.library.service;

public interface ConsumerServiceFactory {
	public <S, T> ConsumerService<S, T> getService(String objectType);
}
