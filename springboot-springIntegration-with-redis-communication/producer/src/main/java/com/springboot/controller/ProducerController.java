package com.springboot.controller;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.messaging.library.intfc.MessageGateway;
import com.springboot.model.Student;

@RestController
@RequestMapping("/producer")
public class ProducerController {

	@Autowired
	private MessageGateway messageLibrary;

	@PostMapping("/sendPost")
	public ResponseEntity<Student> sendStudentInformation(@RequestBody Student student)
			throws InterruptedException, ExecutionException, TimeoutException {
		Future<Student> response = messageLibrary.sendMessage(student, "CONSUMER_QUEUE_1");
		Student studentResponse = response.get(60, TimeUnit.SECONDS);
		return new ResponseEntity<>(studentResponse, HttpStatus.OK);
	}
}
