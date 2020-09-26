package pl.w93c.kafkaread;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public abstract class KafkaReader {

    public void runSingleWorker(String topicName, Properties consumerProperties) {

        org.apache.kafka.clients.consumer.KafkaConsumer kafkaConsumer;

        kafkaConsumer = new org.apache.kafka.clients.consumer.KafkaConsumer<String, byte[]>(consumerProperties);
        kafkaConsumer.subscribe(Arrays.asList(topicName));

        while (true) {

            ConsumerRecords<byte[], byte[]> records = kafkaConsumer.poll(100);

            for (ConsumerRecord<byte[], byte[]> record : records) {

                processRecord(record.key(), record.value());

                {
                    Map<TopicPartition, OffsetAndMetadata> commitMessage = new HashMap<>();
                    commitMessage.put(new TopicPartition(record.topic(), record.partition()),
                            new OffsetAndMetadata(record.offset() + 1));
                    kafkaConsumer.commitSync(commitMessage);
                    // System.out.println("Offset committed to Kafka.");
                }
            }
        }
    }

    protected abstract void processRecord(byte[] key, byte[] value);

}


