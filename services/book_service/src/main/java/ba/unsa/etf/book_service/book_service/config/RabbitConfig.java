package ba.unsa.etf.book_service.book_service.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "reservation.exchange";
    public static final String BOOK_QUEUE = "book.queue";
    public static final String MEMBERSHIP_QUEUE = "membership.queue";
    public static final String BOOK_MEMBERSHIP_CONFIRMED_QUEUE = "book.membership.confirmed.queue";
    public static final String BOOK_MEMBERSHIP_FAILED_QUEUE = "book.membership.failed.queue";

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

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Queue bookMembershipConfirmedQueue() {
        return new Queue("book.membership.confirmed.queue", true);
    }

    @Bean
    public Queue bookMembershipFailedQueue() {
        return new Queue("book.membership.failed.queue", true);
    }

    @Bean
    public Binding bookMembershipConfirmedBinding() {
        return BindingBuilder
                .bind(bookMembershipConfirmedQueue())
                .to(exchange())
                .with("book.membership.confirmed");
    }

    @Bean
    public Binding bookMembershipFailedBinding() {
        return BindingBuilder
                .bind(bookMembershipFailedQueue())
                .to(exchange())
                .with("book.membership.failed");
    }

}

