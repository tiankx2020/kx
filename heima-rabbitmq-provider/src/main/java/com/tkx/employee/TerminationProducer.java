package com.tkx.employee;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author tkx
 * @Date 2025 03 05 21 11
 **/
@Service
public class TerminationProducer {

    private final RabbitTemplate rabbitTemplate;

    public TerminationProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendTerminationMessage(String employeeCode, List<String> regions){
        for (String region : regions) {
            String routingKey = "termination."+region;
            Map<String,String> message = new HashMap<>();
            message.put("employeeCode",employeeCode);
            message.put("action","terminate");

            rabbitTemplate.convertAndSend("emploee.terminatiob",routingKey,message);
            System.out.println("[X] send termination request for "+ employeeCode + " to " + region);
        }
    }
}
