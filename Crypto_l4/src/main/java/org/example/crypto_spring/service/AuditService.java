package org.example.crypto_spring.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.crypto_spring.model.AuditMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class AuditService {

    private static final Logger logger = LoggerFactory.getLogger(AuditService.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendAuditEvent(String eventType, String entityName, Long entityId, Object entity) {
        try {
            String entityJson;
            try {
                entityJson = objectMapper.writeValueAsString(entity);
            } catch (Exception e) {
                entityJson = "{\"error\": \"Failed to serialize entity: " + e.getMessage() + "\"}";
            }

            AuditMessage message = new AuditMessage(eventType, entityName, entityId, entityJson);

            logger.info("Отправка JMS сообщения: {} {} ID: {}", eventType, entityName, entityId);
            jmsTemplate.convertAndSend("audit.topic", message);
            jmsTemplate.convertAndSend("notification.topic", message);

            logger.debug("JMS сообщения отправлены");

        } catch (Exception e) {
            logger.error("Ошибка при отправке события: {}", e.getMessage());
            saveAuditDirectly(eventType, entityName, entityId, entity);
        }
    }

    private void saveAuditDirectly(String eventType, String entityName, Long entityId, Object entity) {
        try {
            logger.warn("Сохранение аудита напрямую потому что JMS недоступен: {} {}", entityName, entityId);
        } catch (Exception ex) {
            logger.error("Ошибка при сохранении: {}", ex.getMessage());
        }
    }
}