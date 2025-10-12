package pl.kurs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pl.kurs.model.EmailNotification;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class KafkaSender {
    private final KafkaTemplate<String, EmailNotification> emailKafkaTemplate;

    public void sendEmailNotification(EmailNotification emailNotification) {
        if (emailNotification.getTimestamp() == null) {
            emailNotification.setTimestamp(LocalDateTime.now());
        }

        emailKafkaTemplate.send("email-notification", emailNotification);
    }

}
