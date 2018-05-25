package com.springboot.messaging.library.service;

public interface ConsumerService<S, T> {
	public T handleServiceRequest(S request);
}
