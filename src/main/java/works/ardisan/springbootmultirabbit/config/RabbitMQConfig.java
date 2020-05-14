package works.ardisan.springbootmultirabbit.config;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    @Bean
    @Primary
    public RabbitTemplate rabbitTemplatePrimary() {
        RabbitTemplate template = new RabbitTemplate(connectionFactoryPrimary());
        template.setMessageConverter(messageConverter());
        return template;
    }

    @Bean
    public RabbitTemplate rabbitTemplateSecondary() {
        RabbitTemplate template = new RabbitTemplate(connectionFactorySecondary());
        template.setMessageConverter(messageConverter());
        return template;
    }

    @Bean
    @Primary
    ConnectionFactory connectionFactoryPrimary() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5677);
        factory.setUsername("rabbitadmin");
        factory.setPassword("rabbitadmin");
        return factory;
    }

    @Bean
    ConnectionFactory connectionFactorySecondary() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5678);
        factory.setUsername("rabbitadmin");
        factory.setPassword("rabbitadmin");
        return factory;
    }

    @Bean
    @Primary
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerPrimary() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactoryPrimary());
        factory.setMessageConverter(messageConverter());
        return factory;  
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerSecondary() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactorySecondary());
        factory.setMessageConverter(messageConverter());
        return factory;  
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}