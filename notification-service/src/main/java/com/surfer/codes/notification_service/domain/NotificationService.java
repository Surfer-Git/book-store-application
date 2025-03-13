package com.surfer.codes.notification_service.domain;

import com.surfer.codes.notification_service.ApplicationProperties;
import com.surfer.codes.notification_service.events.models.CancelledOrderEvent;
import com.surfer.codes.notification_service.events.models.CreateOrderEvent;
import com.surfer.codes.notification_service.events.models.DeliveredOrderEvent;
import com.surfer.codes.notification_service.events.models.ErrorOrderEvent;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private Logger log = LoggerFactory.getLogger(NotificationService.class);
    private final ApplicationProperties properties;
    private final JavaMailSender emailSender;

    public NotificationService(ApplicationProperties applicationProperties, JavaMailSender emailSender) {
        this.properties = applicationProperties;
        this.emailSender = emailSender;
    }

    public void sendOrderEventNotification(CreateOrderEvent event) {
        String message =
                """
                ===================================================
                Order Created Notification
                ----------------------------------------------------
                Dear %s,
                Your order with orderNumber: %s has been created successfully.

                Thanks,
                BookStore Team
                ===================================================
                """
                        .formatted(event.customer().name(), event.orderNumber());
        log.info("\n{}", message);
        sendEmail(event.customer().email(), "order created notification", message);
    }

    public void sendOrderEventNotification(DeliveredOrderEvent event) {
        String message =
                """
                ===================================================
                Order Delivered Notification
                ----------------------------------------------------
                Dear %s,
                Your order with orderNumber: %s has been delivered successfully.

                Thanks,
                BookStore Team
                ===================================================
                """
                        .formatted(event.customer().name(), event.orderNumber());
        log.info("\n{}", message);
        sendEmail(event.customer().email(), "order delivered notification", message);
    }

    public void sendOrderEventNotification(CancelledOrderEvent event) {
        String message =
                """
                ===================================================
                Order Cancelled Notification
                ----------------------------------------------------
                Dear %s,
                Your order with orderNumber: %s has been cancelled successfully.

                Thanks,
                BookStore Team
                ===================================================
                """
                        .formatted(event.customer().name(), event.orderNumber());
        log.info("\n{}", message);
        sendEmail(event.customer().email(), "order cancelled notification", message);
    }

    public void sendOrderEventNotification(ErrorOrderEvent event) {
        String message =
                """
                ===================================================
                Order Error Notification
                ----------------------------------------------------
                Hi Team,
                the order with orderNumber: %s could not be processed due to an Error.

                Thanks,
                Dev Team
                ===================================================
                """
                        .formatted(event.customer().name(), event.orderNumber());
        log.info("\n{}", message);
        sendEmail(properties.supportTeamEmail(), "order error notification", message);
    }

    private void sendEmail(String recipient, String subject, String content) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom(properties.supportTeamEmail());
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setText(content);
            emailSender.send(mimeMessage);
            log.info("Email sent to: {}", recipient);
        } catch (Exception e) {
            throw new RuntimeException("Error while sending email", e);
        }
    }
}
