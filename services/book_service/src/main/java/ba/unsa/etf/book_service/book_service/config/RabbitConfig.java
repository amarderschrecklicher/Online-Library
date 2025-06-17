package ba.unsa.etf.book_service.book_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "reservation.exchange";
    public static final String BOOK_QUEUE = "book.queue";
    public static final String MEMBERSHIP_QUEUE = "membership.queue";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue bookQueue() {
        return new Queue(BOOK_QUEUE);
    }

    @Bean
    public Binding bookBinding() {
        return BindingBuilder.bind(bookQueue()).to(exchange()).with("book.#");
    }
}

