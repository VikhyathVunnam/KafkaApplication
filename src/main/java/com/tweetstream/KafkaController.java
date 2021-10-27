package com.tweetstream;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class KafkaController {
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@PostMapping(value = "/api/send/{data}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> sendMessage(@PathVariable(name = "data") String data) {
		try {
			// Sending the message to kafka topic queue
			kafkaTemplate.send("tweetTopic", data).get();
			System.out.println("send");
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

}
