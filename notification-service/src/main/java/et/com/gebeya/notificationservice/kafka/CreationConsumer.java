package et.com.gebeya.notificationservice.kafka;

import et.com.gebeya.notificationservice.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CreationConsumer {
    @Autowired
    private EmailService emailService;
    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )


    public void handleNotification(ClientCreatedEvent clientCreatedEvent){
        String clientEmail = clientCreatedEvent.getClientEmail()+"@gmail.com";
        String fullName=clientCreatedEvent.getClientName();
       emailService.sendEmailNotification(clientEmail,fullName);
        //email Clients info
        log.info("Received Notification for Client {}",clientCreatedEvent.getClientEmail());

    }

}
