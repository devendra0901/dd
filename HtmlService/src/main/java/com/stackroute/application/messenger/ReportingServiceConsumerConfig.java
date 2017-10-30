package com.stackroute.application.messenger;


import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.stackroute.application.model.HtmlModel;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;


@Configuration
@EnableKafka
public class ReportingServiceConsumerConfig {
	
	@Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;
    
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;
    
    @Bean
    public ConsumerFactory<String, HtmlModel> consumerFactory() {
        Map<String, Object> props = new HashMap<String, Object>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<String, HtmlModel>(props,
                                          new StringDeserializer(),
                                          new JsonDeserializer<HtmlModel>(HtmlModel.class));
    }
    
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, HtmlModel> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, HtmlModel> factory = new ConcurrentKafkaListenerContainerFactory<String, HtmlModel>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
