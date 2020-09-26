package pl.w93c.kafkaread;

import pl.w93c.kafkaread.data.Pogoda;

public class PogodaKafkaReader extends AvroKafkaReader<Pogoda> {

    public PogodaKafkaReader() {
        super(Pogoda.class);
    }

    @Override
    protected void processRecord(Pogoda pogoda) {
        System.out.println("Read Pogoda record: " + pogoda);
    }
}
