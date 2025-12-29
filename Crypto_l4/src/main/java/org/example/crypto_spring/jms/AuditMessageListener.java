package org.example.crypto_spring.jms;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.crypto_spring.entity.AuditLog;
import org.example.crypto_spring.model.AuditMessage;
import org.example.crypto_spring.repository.AuditLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class AuditMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(AuditMessageListener.class);

    @Autowired
    private AuditLogRepository auditLogRepository;

    @JmsListener(destination = "audit.topic", containerFactory = "jmsListenerContainerFactory")
    public void receiveMessage(AuditMessage auditMessage) {
        try {
            logger.info("Получено JMS сообщение: {} {} ID: {}",
                    auditMessage.getEventType(),
                    auditMessage.getEntityName(),
                    auditMessage.getEntityId());

            AuditLog auditLog = new AuditLog();
            auditLog.setEventTime(auditMessage.getTimestamp());
            auditLog.setEventType(auditMessage.getEventType());
            auditLog.setEntityName(auditMessage.getEntityName());
            auditLog.setEntityId(auditMessage.getEntityId());
            auditLog.setChanges(auditMessage.getEntityJson());

            auditLogRepository.save(auditLog);

            logger.info("Записано в БД: {} {} ID: {}",
                    auditMessage.getEventType(),
                    auditMessage.getEntityName(),
                    auditMessage.getEntityId());

            System.out.println("Запись сохранена");
            System.out.println("Тип: " + auditMessage.getEventType());
            System.out.println("Сущность: " + auditMessage.getEntityName());
            System.out.println("ID: " + auditMessage.getEntityId());
            System.out.println("Время: " + auditMessage.getTimestamp());

        } catch (Exception e) {
            logger.error("Ошибка при записи лога: {}", e.getMessage(), e);
        }
    }
}