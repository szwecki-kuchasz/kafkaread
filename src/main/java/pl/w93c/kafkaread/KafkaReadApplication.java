package pl.w93c.kafkaread;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class KafkaReadApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaReadApplication.class, args);
    }

}
