package com.kafkaobject.kafka.producer;
import java.util.HashMap;
import java.util.Map;
 
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.kafkaobject.model.ProduceModel;
import com.kafkaobject.model.ProjectInfo;
 
 
@Configuration
public class SenderConfig {
	
//	@Value("${spring.kafka.bootstrap-servers}")
//	private String bootstrapServer;
	//server to produce to
	  @Value("${kafka.bootstrap-servers}")
	  private String bootstrapServer;
//	
	@Bean
	public ProducerFactory<String, ProduceModel> producerFactory() {
	    Map<String, Object> configProps = new HashMap<>();
	    configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
	    configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	    configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
	    return new DefaultKafkaProducerFactory<>(configProps);
	}
	 
	@Bean
	public KafkaTemplate<String, ProduceModel> kafkaTemplate() {
	    return new KafkaTemplate<>(producerFactory());
	}
}
//	  @Value("${kafka.bootstrap-servers}")
//	  private String bootstrapServers;
//
//	  @Bean
//	  public Map<String, Object> producerConfigs() {
//	    Map<String, Object> props = new HashMap<>();
//	    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//	    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//	    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//	    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//
//	    return props;
//	  }
//
//	  @Bean
//	  public ProducerFactory<String, ProjectInfo> producerFactory() {
//	    return new DefaultKafkaProducerFactory<>(producerConfigs());
//	  }
//
//	  @Bean
//	  public KafkaTemplate<String, ProjectInfo> kafkaTemplate() {
//	    return new KafkaTemplate<>(producerFactory());
//	  }
//
//	  @Bean
//	  public Sender sender() {
//	    return new Sender();
//	  }
//	}