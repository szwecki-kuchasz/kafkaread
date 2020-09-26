package pl.w93c.kafkaread;

import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificRecord;

import java.io.IOException;

 public abstract class AvroKafkaReader<T extends SpecificRecord> extends KafkaReader {

     final Class<T> typeParameterClass;

     protected AvroKafkaReader(Class<T> typeParameterClass) {
         this.typeParameterClass = typeParameterClass;
     }

     @Override
    protected final void processRecord(byte[] key, byte[] value) {
        // convert bytes to T instance
        try {
            T t = deserializeT(value);
            processRecord(t);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

     protected abstract void processRecord(T t);

     private T deserializeT(byte[] data) throws IOException {
        DatumReader<T> reader
                = new SpecificDatumReader<>(typeParameterClass);
        try {
            Decoder decoder = DecoderFactory.get().binaryDecoder(data, null);
            return reader.read(null, decoder);
        } catch (Exception e) {
            System.out.println("Deserialization error:" + e.getMessage());
            throw e;
        }
    }

}
