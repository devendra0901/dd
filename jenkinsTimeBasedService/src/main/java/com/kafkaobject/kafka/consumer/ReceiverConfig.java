//package com.kafkaobject.kafka.consumer;
//
//import java.util.HashMap;
//import java.util.Map;
// 
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//
//
//import com.kafkaobject.model.ProjectInfo;
// 
// 
//@EnableKafka
//@Configuration
//public class ReceiverConfig {
// 
//	@Value("${kafka.bootstrap-servers}")
//	private String bootstrapServer;
//	
//	@Value("kafka-group")
//	private String groupId;
//	
//	@Bean
//	public ConsumerFactory<String, ProjectInfo> consumerFactory() {
//	    Map<String, Object> props = new HashMap<>();
//	    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
//	    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
//	    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//	    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//	    return new DefaultKafkaConsumerFactory<>(props,
//							    	      new StringDeserializer(), 
//							    	      new JsonDeserializer<>(ProjectInfo.class));
//	}
//	 
//	@Bean
//	public ConcurrentKafkaListenerContainerFactory<String, ProjectInfo> kafkaListenerContainerFactory() {
//	    ConcurrentKafkaListenerContainerFactory<String, ProjectInfo> factory = new ConcurrentKafkaListenerContainerFactory<>();
//	    factory.setConsumerFactory(consumerFactory());
//	    return factory;
//	}
//}
////	@Value("${kafka.bootstrap-servers}")
////	  private String bootstrapServers;
////
////	  @Bean
////	  public Map<String, Object> consumerConfigs() {
////	    Map<String, Object> props = new HashMap<>();
////	    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
////	    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
////	    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
////	    props.put(ConsumerConfig.GROUP_ID_CONFIG, "reveiver");
////	    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
////
////	    return props;
////	  }
////
////	  @Bean
////	  public ConsumerFactory<String, ProjectInfo> consumerFactory() {
////	    return new DefaultKafkaConsumerFactory<>(consumerConfigs());
////	  }
////
////	  @Bean
////	  public ConcurrentKafkaListenerContainerFactory<String, ProjectInfo> kafkaListenerContainerFactory() {
////	    ConcurrentKafkaListenerContainerFactory<String, ProjectInfo> factory =
////	        new ConcurrentKafkaListenerContainerFactory<>();
////	    factory.setConsumerFactory(consumerFactory());
////
////	    return factory;
////	  }
////
////	  @Bean
////	  public Receiver receiver() {
////	    return new Receiver();
////	  }
////	}
package com.kafkaobject.kafka.consumer;
import java.util.HashMap;
import java.util.Map;
 
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
 
import com.kafkaobject.model.ProjectInfo;
 
 
@EnableKafka
@Configuration
public class ReceiverConfig {
 
	@Value("${kafka.bootstrap-servers}")
	private String bootstrapServer;
	
	@Value("${spring.kafka.consumer.group-id}")
	private String groupId;
	
	@Bean
	public ConsumerFactory<String, ProjectInfo> consumerFactory() {
	    Map<String, Object> props = new HashMap<String, Object>();
	    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
	    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
	    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
	    return new DefaultKafkaConsumerFactory<String, ProjectInfo>(props,
							    	      new StringDeserializer(), 
							    	      new JsonDeserializer<ProjectInfo>(ProjectInfo.class));
	}
	 
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, ProjectInfo> kafkaListenerContainerFactory() {
	    ConcurrentKafkaListenerContainerFactory<String, ProjectInfo> factory = new ConcurrentKafkaListenerContainerFactory<String, ProjectInfo>();
	    factory.setConsumerFactory(consumerFactory());
	    return factory;
	}
}
