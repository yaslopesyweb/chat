package br.com.fiap.chat.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String CHAT_QUEUE = "chat-queue";

    @Bean
    public Queue chatQueue() {
        return new Queue(CHAT_QUEUE, false);
    }
    public static final String CHAT_EXCHANGE = "chat-exchange";
    public static final String CHAT_ROUTING_KEY = "chat.#";

    @Bean
    public TopicExchange chatExchange() {
        return new TopicExchange(CHAT_EXCHANGE);
    }

    @Bean
    public Binding chatBinding(Queue chatQueue, TopicExchange chatExchange) {
        return BindingBuilder.bind(chatQueue).to(chatExchange).with(CHAT_ROUTING_KEY);
    }
}
