package works.ardisan.springbootmultirabbit.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class QueueSenderService {

    @Autowired
    private RabbitTemplate rabbitPrimary;

    @Autowired
    @Qualifier(value = "rabbitTemplateSecondary")
    private RabbitTemplate rabbitSecondary;

    @Autowired
    private ObjectMapper objMapper;

    public void publishToPrimaryQueue(String name) throws AmqpException {
        System.out.println(">>> Publish to primary queue");
        ObjectNode onode = objMapper.createObjectNode();
        onode.put("primaryName", name);
        rabbitPrimary.convertAndSend("PrimaryQueue", onode);
    }
    
    public void publishToSecondaryQueue(String name) throws AmqpException {
        System.out.println(">>> Publish to secondary queue");
        ObjectNode onode = objMapper.createObjectNode();
        onode.put("secondaryName", name);
        rabbitSecondary.convertAndSend("SecondaryQueue", onode);
    }
}