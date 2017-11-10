/*package com.stackroute.deploymentdashboard.usermanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.stackroute.deploymentdashboard.usermanagement.domains.UserModel;

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

	@Autowired
	private KafkaTemplate<String, UserModel> kafkaTemplate;

	@Value("${kafka.topic.bootnew}")
	private String kafkaTopic;
	private String key = "user";

	@Override
	public void produce(UserModel user) {
		System.out.println("sending data =" + user);
		kafkaTemplate.send(this.kafkaTopic, key, user);
	}

}
*/