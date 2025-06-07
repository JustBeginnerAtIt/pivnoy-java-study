package ttv.poltoraha.pivka.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

// Как правило все имеющиеся метрики создаются в отдельном классе.
@Component
public class CustomMetrics {

    public enum MetricType {
        CREATE_AUTHOR,
        DELETE_AUTHOR,
        ADD_BOOKS_TO_AUTHOR
    }

    private final Map<MetricType, Counter> counters = new EnumMap<>(MetricType.class);

    private final Map<MetricType, Timer> timers = new EnumMap<>(MetricType.class);

    @Autowired
    public CustomMetrics(MeterRegistry meterRegistry) {
        counters.put(MetricType.CREATE_AUTHOR, Counter
                .builder("author.controller.create.counter")
                .description("Количесвтво вызовов создания автора")
                .register(meterRegistry));

        timers.put(MetricType.CREATE_AUTHOR, meterRegistry.timer(
                "author.controller.create.timer",
                List.of(Tag.of("description", "Время выполнения метода создания автора"))));

        counters.put(MetricType.DELETE_AUTHOR, Counter
                .builder("author.controller.delete.counter")
                .description("Количество вызовов удаления автора")
                .register(meterRegistry));

        timers.put(MetricType.DELETE_AUTHOR, meterRegistry.timer(
                "author.controller.delete.timer",
                List.of(Tag.of("description", "Время выполнения удаления автора"))));

        counters.put(MetricType.ADD_BOOKS_TO_AUTHOR, Counter
                .builder("author.controller.addbooks.counter")
                .description("Количество вызовов добавления книг автору")
                .register(meterRegistry));

        timers.put(MetricType.ADD_BOOKS_TO_AUTHOR, meterRegistry.timer(
                "author.controller.addbooks.timer",
                List.of(Tag.of("description", "Время выполнения добавления книг автору"))));
    }

    // методы для изменения метрик
    public void recordCounter(MetricType metricType) {
        counters.get(metricType).increment();
    }

    public void recordTimer(MetricType metricType, Long durationMs) {
        timers.get(metricType).record(Duration.ofMillis(durationMs));
    }


}
