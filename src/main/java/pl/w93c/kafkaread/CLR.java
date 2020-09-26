package pl.w93c.kafkaread;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class CLR implements CommandLineRunner {

    @Value("${kafka.bootstrap.servers}")
    private String kafkaBootstrapServers;

    @Value("${zookeeper.groupId}")
    private String zookeeperGroupId;

    @Value("${kafka.topic.thetechcheck}")
    private String theTechCheckTopicName;


    @Override
    public void run(String... args) {
        Properties consumerProperties = new Properties();

        consumerProperties.put("bootstrap.servers", kafkaBootstrapServers);
        consumerProperties.put("group.id", zookeeperGroupId);
//        consumerProperties.put("zookeeper.session.timeout.ms", "6000");
//        consumerProperties.put("zookeeper.sync.time.ms", "2000");
//        consumerProperties.put("auto.commit.enable", "false");
//        consumerProperties.put("auto.commit.interval.ms", "1000");
//        consumerProperties.put("consumer.timeout.ms", "-1");
//        consumerProperties.put("max.poll.records", "1");
        consumerProperties.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        consumerProperties.put("key.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");

        runKafkaReader(new SimpleStringKafkaReader(), theTechCheckTopicName, consumerProperties);
        runKafkaReader(new PogodaKafkaReader(), "avroPogoda", consumerProperties);
    }

    protected void runKafkaReader(KafkaReader kafkaReader, String topic, Properties consumerProperties) {
        Thread kafkaConsumerThread = new Thread(() -> {
            System.out.println("Starting Kafka consumer thread.");
            kafkaReader.runSingleWorker(topic, consumerProperties);
        });
        kafkaConsumerThread.start();
    }

}
