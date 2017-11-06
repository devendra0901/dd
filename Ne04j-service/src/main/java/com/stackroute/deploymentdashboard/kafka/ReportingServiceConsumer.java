package com.stackroute.deploymentdashboard.kafka;


import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.stackroute.deploymentdashboard.model.ProjectModel;
import com.stackroute.deploymentdashboard.model.UserModel;

@Service
public class ReportingServiceConsumer {
	
	  private static final Logger LOGGER = LoggerFactory.getLogger(ReportingServiceConsumer.class);
	
	  private CountDownLatch latch = new CountDownLatch(1);

	  public CountDownLatch getLatch() {
	    return latch;
	  }
	  
	  //@KafkaListener(topics = "topicName", group = "foo")
	  
	/*@KafkaListener(topics="${spring.kafka.consumer.group-id}",
			containerFactory = "kafkaListenerContainerFactory")
	public void listen(String message) {
	    System.out.println("Received Messasge in group foo: " + message);
	}*/
	   
	
	@KafkaListener(topics = "${spring.kafka.consumer.group-id}",
	   containerFactory = "projectModelKafkaListenerContainerFactory")
	   public void projectModellistener(ProjectModel projectModel) {
		 latch.countDown();
	   System.out.println(projectModel.isStatus_active_project());
	} 
	
	
	@KafkaListener(topics = "${spring.kafka.consumer.group-id}", 
	   containerFactory = "userModelKafkaListenerContainerFactory")
	   public void projectModelListener(UserModel userModel) {
		 latch.countDown();
	   System.out.println(userModel);
	   System.out.println(userModel.getEmailId());
	}

	

}
