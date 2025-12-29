package org.example.crypto_spring.jms;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.crypto_spring.model.AuditMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class NotificationMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(NotificationMessageListener.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${app.notification.email:admin@crypto-system.com}")
    private String notificationEmail;

    @Value("${app.notification.enabled:true}")
    private boolean notificationsEnabled;

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @JmsListener(destination = "notification.topic", containerFactory = "jmsListenerContainerFactory")
    public void receiveMessage(AuditMessage auditMessage) {
        if (!notificationsEnabled) {
            logger.info("Уведомления отключены");
            return;
        }

        logger.info("Получено JMS уведомление: {} {} ID: {}",
                auditMessage.getEventType(),
                auditMessage.getEntityName(),
                auditMessage.getEntityId());

        try {
            // Проверяем условия для отправки email
            if (shouldSendNotification(auditMessage)) {
                logger.info("Отправляем email");
                sendEmailNotification(auditMessage);
            } else {
                logger.info("Условия не выполнены поэтому уведомление не будет отправлено");
            }

        } catch (Exception e) {
            logger.error("Ошибка при обработке уведомления: {}", e.getMessage(), e);
        }
    }

    private boolean shouldSendNotification(AuditMessage auditMessage) {
        try {
            if ("DELETE".equals(auditMessage.getEventType())) {
                logger.debug("DELETE событие");
                return true;
            }

            if ("CryptoCoin".equals(auditMessage.getEntityName())) {
                JsonNode node = objectMapper.readTree(auditMessage.getEntityJson());
                if (node.has("priceUsd")) {
                    double price = node.get("priceUsd").asDouble();
                    boolean conditionMet = price > 10000;
                    logger.debug("Условие CryptoCoin: цена {} > 10000 = {}", price, conditionMet);
                    return conditionMet;
                }
            }
            if ("Exchange".equals(auditMessage.getEntityName())) {
                JsonNode node = objectMapper.readTree(auditMessage.getEntityJson());
                if (node.has("country")) {
                    String country = node.get("country").asText().toLowerCase();
                    boolean conditionMet = country.contains("usa") || country.contains("united states");
                    logger.debug("Условие Exchange: страна {} содержит USA = {}", country, conditionMet);
                    return conditionMet;
                }
            }

            logger.debug("Условия не выполнены");
            return false;

        } catch (Exception e) {
            logger.error("Ошибка при проверке условий: {}", e.getMessage());
            return false;
        }
    }

    private void sendEmailNotification(AuditMessage auditMessage) {
        try {
            System.out.println("ОТПРАВКА EMAIL YANDEX");

            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("n6305-010302D@yandex.ru");
            message.setTo(notificationEmail);

            message.setSubject(createEmailSubject(auditMessage));
            String text = createEmailBody(auditMessage);
            message.setText(text);
            mailSender.send(message);

            System.out.println("EMAIL ОТПРАВЛЕН, УРААА!");
            System.out.println("Почта с которой отправляем: n6305-010302D@yandex.ru");
            System.out.println("Почта на которую отправляем: " + notificationEmail);

        } catch (Exception e) {
            System.err.println("\n:EMAIL НЕ отправлен, ....((");
            System.err.println("Сообщение: " + e.getMessage());

            if (e.getMessage().contains("Bad address mailbox syntax")) {
                System.err.println("\nНеправильный адрес отправляющей почты!");
            }
            e.printStackTrace();
        }
    }

    private String createEmailSubject(AuditMessage auditMessage) {
        return String.format("[Crypto System] %s: %s (ID: %d)",
                auditMessage.getEventType(),
                auditMessage.getEntityName(),
                auditMessage.getEntityId());
    }

    private String createEmailBody(AuditMessage auditMessage) {
        StringBuilder body = new StringBuilder();
        body.append("Системное уведомление\n");

        body.append(String.format("Тип события: %s\n", auditMessage.getEventType()));
        body.append(String.format("Сущность: %s\n", auditMessage.getEntityName()));
        body.append(String.format("ID сущности: %d\n", auditMessage.getEntityId()));
        body.append(String.format("Время события: %s\n",
                auditMessage.getTimestamp().format(formatter)));
        body.append(String.format("Время обработки: %s\n\n",
                LocalDateTime.now().format(formatter)));

        try {
            JsonNode node = objectMapper.readTree(auditMessage.getEntityJson());
            body.append("Данные сущности:\n");

            if ("CryptoCoin".equals(auditMessage.getEntityName())) {
                if (node.has("name")) body.append(String.format("Название: %s\n", node.get("name").asText()));
                if (node.has("symbol")) body.append(String.format("Символ: %s\n", node.get("symbol").asText()));
                if (node.has("priceUsd")) body.append(String.format("Цена: %s\n", node.get("priceUsd").asText()));
                if (node.has("marketCap")) body.append(String.format("Рыночная капитализация: %s\n", node.get("marketCap").asText()));
            } else if ("Exchange".equals(auditMessage.getEntityName())) {
                if (node.has("name")) body.append(String.format("Название: %s\n", node.get("name").asText()));
                if (node.has("country")) body.append(String.format("Страна: %s\n", node.get("country").asText()));
                if (node.has("website")) body.append(String.format("Вебсайт: %s\n", node.get("website").asText()));
            }
        } catch (Exception e) {
            body.append("Данные: ").append(auditMessage.getEntityJson()).append("\n");
        }

        return body.toString();
    }
}