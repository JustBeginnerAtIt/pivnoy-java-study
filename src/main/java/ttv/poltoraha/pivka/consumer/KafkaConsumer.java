package ttv.poltoraha.pivka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ttv.poltoraha.pivka.dao.request.BookDTO;
import ttv.poltoraha.pivka.repository.BookRepository;
import ttv.poltoraha.pivka.service.BookDtoToBookService;
import ttv.poltoraha.pivka.serviceImpl.BookDtoToBookServiceImpl;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


// Пример Kafka consumer`a через Spring Kafka.
@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final BookDtoToBookService bookDtoToBookService;
    private final ObjectMapper objectMapper;
    private final BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();

    @KafkaListener(topics = "your-topic", groupId = "my-group")
    public void listen(String message) {
        log.info("Received Kafka message: {}", message);
        messageQueue.offer(message);
    }

    @Scheduled(fixedRate = 1000)
    public void processMessage() {

        Integer maxMessagesPerSecond = 15;

        for (int i = 0; i < maxMessagesPerSecond; i++) {
            String message = messageQueue.poll();
            if (message == null) break;

            try {
                BookDTO dto = objectMapper.readValue(message, BookDTO.class);
                bookDtoToBookService.saveBook(dto);
                log.info("Saved Book: {}", dto.getArticle());
            } catch (Exception e) {
                log.error("Failed to process message: {}", message, e);
            }
        }
    }
}
