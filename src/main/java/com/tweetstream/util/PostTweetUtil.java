package com.tweetstream.util;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.mongodb.client.model.changestream.ChangeStreamDocument;

@Component
public class PostTweetUtil {
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void processDocument(ChangeStreamDocument<Document> doc) {
		Document tweetDoc = doc.getFullDocument();
		kafkaTemplate.send("tweetTopic", tweetDoc.toJson());
	}
}
