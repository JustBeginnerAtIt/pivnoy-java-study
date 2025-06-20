package ttv.poltoraha.pivka.app.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import ttv.poltoraha.pivka.dao.request.BookDTO;
import ttv.poltoraha.pivka.repository.BookRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = "your-topic", brokerProperties = {
        "listeners=PLAINTEXT://localhost:9092", "port=9092"
})
public class KafkaConsumerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", () -> "localhost:9092");
    }

    @Test
    public void testKafkaConsumer() throws Exception {
        Map<String, Object> kafkaConfig = new HashMap<>(KafkaTestUtils.producerProps("localhost:9092"));
        kafkaConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        kafkaConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(kafkaConfig));

        BookDTO dto = BookDTO.builder()
                .article("Доктор Живаго")
                .genreName("Роман")
                .rating(0.0)
                .tags("Любовь, Лоли")
                .authorFullName("Пастернак Борис Леонидович")
                .build();

        String json = objectMapper.writeValueAsString(dto);

        kafkaTemplate.send("your-topic", json);

        await().atMost(10, TimeUnit.SECONDS).untilAsserted(() ->
                assertThat(bookRepository.findAll()).anyMatch(b -> b.getArticle().equals("Доктор Живаго"))
        );
    }

}
