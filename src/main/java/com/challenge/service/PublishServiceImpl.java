package com.challenge.service;

import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.AuthorizationException;
import org.apache.kafka.common.errors.OutOfOrderSequenceException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Service;

import com.challenge.domain.Location;

@Service("publishService")
public class PublishServiceImpl implements PublishService {

	@Override
	public boolean publishLocations(List<Location> locations) {

		 Properties props = new Properties();
		 props.put("bootstrap.servers", "localhost:9092");
		 props.put("transactional.id", "my-transactional-id");
		 Producer<String, String> producer = new KafkaProducer<>(props, new StringSerializer(), new StringSerializer());
		
		 producer.initTransactions();
		
		 try {
		     producer.beginTransaction();
		     locations.forEach(x -> producer.send(
		    		 new ProducerRecord<>("my-topic", x.toString()), (metadata, e) -> {
		                     if(e != null) {
		                        e.printStackTrace();
		                     } else {
		                        System.out.println("The offset of the record we just sent is: " + metadata.offset());
		                     }
		                 }));
		     producer.commitTransaction();
		 } catch (ProducerFencedException | OutOfOrderSequenceException | AuthorizationException e) {
		     // We can't recover from these exceptions, so our only option is to close the producer and exit.
		     producer.close();
		 } catch (KafkaException e) {
		     // For all other exceptions, just abort the transaction and try again.
		     producer.abortTransaction();
		 }
		 producer.close();
		 
		return true;
	}

}
