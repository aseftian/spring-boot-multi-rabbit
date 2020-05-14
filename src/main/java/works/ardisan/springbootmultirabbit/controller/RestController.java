package works.ardisan.springbootmultirabbit.controller;

import org.springframework.amqp.AmqpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import works.ardisan.springbootmultirabbit.service.QueueSenderService;


@Controller
@RequestMapping("/api")
public class RestController {

    @Autowired
    private QueueSenderService senderService;
    
    @SuppressWarnings("rawtypes")
    @RequestMapping(value="/primary/{name}", method=RequestMethod.GET)
    public ResponseEntity primaryQueue(@PathVariable final String name) {
        try {
            senderService.publishToPrimaryQueue(name);
        } catch (AmqpException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(String.format("Hallo %s!", name));
    }
    
    @SuppressWarnings("rawtypes")
    @RequestMapping(value="/secondary/{name}", method=RequestMethod.GET)
    public ResponseEntity secondaryQueue(@PathVariable final String name) {
        try {
            senderService.publishToSecondaryQueue(name);
        } catch (AmqpException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(String.format("Hallo %s!", name));
    }
}