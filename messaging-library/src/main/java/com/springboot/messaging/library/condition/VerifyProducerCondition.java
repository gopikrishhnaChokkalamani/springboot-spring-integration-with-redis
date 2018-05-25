package com.springboot.messaging.library.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class VerifyProducerCondition implements Condition {

	@Override
	public boolean matches(ConditionContext arg0, AnnotatedTypeMetadata arg1) {
		// TODO Auto-generated method stub
		return "producer".equalsIgnoreCase(arg0.getEnvironment().getProperty("message.library.application"));
	}

}