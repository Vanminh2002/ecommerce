package com.example.notificationservice.listeners;

import com.example.notificationservice.event.OrderPlaceEvent;
import com.example.notificationservice.service.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderEventListener {
    EmailService emailService;

    @KafkaListener(topics = "order-topic", groupId = "notification-group", containerFactory = "orderPlaceEventContainerFactory")
    // orderPlaceEventContainerFactory lấy từ file config
    public void handleListener(OrderPlaceEvent event) {
        System.out.println("Nhận được envent từ kafka: " + event);
        emailService.sendOrderToEmail(event);
    }
}
