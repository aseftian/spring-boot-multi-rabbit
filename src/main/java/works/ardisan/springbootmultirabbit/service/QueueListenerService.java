package works.ardisan.springbootmultirabbit.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class QueueListenerService {

    @RabbitListener(containerFactory = "rabbitListenerContainerPrimary", queues = "${spring.rabbitmq.primary.listen.queue}")
    public void listenPrimaryQueue(Message message) {
        System.out.println(">>> [1] Consuming primary message: " + new String(message.getBody()));
    }

    @RabbitListener(containerFactory = "rabbitListenerContainerSecondary", queues = "${spring.rabbitmq.secondary.listen.queue}")
    public void listenSecondaryQueue(Message message) {
        System.out.println(">>> [2] Consuming secondary message: " + new String(message.getBody()));
    }
    
}