package com.challenge.config;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaProducerConfig {
	
	@Value("${kafka.url}")
	private String kafkaUrl;
	
	@Value("${kafka.transactional.id}")
	private String kafkaTransacionID;
	
	@Bean
	public Producer<String, String> kafkaProducer() {

		 Properties props = new Properties();
		 props.put("bootstrap.servers", kafkaUrl);
		 props.put("transactional.id", kafkaTransacionID);
		 
		 return new KafkaProducer<>(props, new StringSerializer(), new StringSerializer());
	}
}
