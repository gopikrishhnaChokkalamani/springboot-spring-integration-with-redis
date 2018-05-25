package com.springboot.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.springboot.messaging.library.service.ConsumerService;
import com.springboot.model.Student;

@Service("Student")
@ComponentScan({ "com.springboot.messaging.library" })
public class Consumer1Service implements ConsumerService<Student, Student> {

	@Override
	public Student handleServiceRequest(Student request) {
		// TODO Auto-generated method stub
		return request;
	}
}
