package com.challenge.service;

import java.util.List;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.AuthorizationException;
import org.apache.kafka.common.errors.OutOfOrderSequenceException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.domain.Location;

@Service
public class PublishServiceImpl implements PublishService {

	@Autowired
	private Producer<String, String> kafkaProducer;
	
	private PublishService p;
	
	@Override
	public boolean publishLocations(List<Location> locations) {
		
		boolean success = true;
		
		kafkaProducer.initTransactions();
		
		 try {
		     kafkaProducer.beginTransaction();
		     locations.forEach(x -> kafkaProducer.send(
		    		 new ProducerRecord<>("my-topic", x.toString()), (metadata, e) -> {
		                     if(e != null) {
		                        e.printStackTrace();
		                     } else {
		                        System.out.println("The offset of the record we just sent is: " + metadata.offset());
		                     }
		                 }));
		     kafkaProducer.commitTransaction();
		 } catch (ProducerFencedException | OutOfOrderSequenceException | AuthorizationException e) {
		     // We can't recover from these exceptions, so our only option is to close the kafkaProducer and exit.
			 success = false;
		     kafkaProducer.close();
		 } catch (KafkaException e) {
		     // For all other exceptions, just abort the transaction and try again.
			 success = false;
			 kafkaProducer.abortTransaction();
		 }
		 kafkaProducer.close();
		 
		return success;
	}

}
