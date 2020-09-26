package pl.w93c.kafkaread;

public class SimpleStringKafkaReader extends KafkaReader {

    @Override
    protected void processRecord(byte[] key, byte[] value) {
        String message = new String(value) + " (as String)";
        System.out.println("Received message: " + message);
    }

}
