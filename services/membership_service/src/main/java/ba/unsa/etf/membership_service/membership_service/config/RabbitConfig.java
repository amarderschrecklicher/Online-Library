package ba.unsa.etf.membership_service.membership_service.config;

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
    public Queue membershipQueue() {
        return new Queue(MEMBERSHIP_QUEUE);
    }


    @Bean
    public Binding membershipBinding() {
        return BindingBuilder.bind(membershipQueue()).to(exchange()).with("membership.#");
    }
}

