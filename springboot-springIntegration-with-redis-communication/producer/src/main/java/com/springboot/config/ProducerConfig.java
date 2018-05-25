package com.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.springboot.interceptor.ProducerInterceptor;

@Configuration
@ComponentScan("com.springboot")
public class ProducerConfig extends WebMvcConfigurerAdapter{

	@Autowired
	private ProducerInterceptor interceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(interceptor);
	}
	
	
}
