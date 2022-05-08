package me.motemere.middleproxy.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaTopicConfig {

  @Value(value = "${kafka.bootstrapAddress}")
  private String bootstrapAddress;

  @Value(value = "${message.topic.name}")
  private String topicName;


  /**
   * Makes a KafkaAdmin bean.
   *
   * @return the KafkaAdmin.
   */
  @Bean
  public KafkaAdmin kafkaAdmin() {
    Map<String, Object> configs = new HashMap<>();
    configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
    return new KafkaAdmin(configs);
  }

  /**
   * Creates a Kafka topic.
   *
   * @return the topic.
   */
  @Bean
  public NewTopic topic() {
    return new NewTopic(topicName, 1, (short) 1);
  }
}
