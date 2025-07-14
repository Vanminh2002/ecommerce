package com.example.notificationservice.service;

import com.example.notificationservice.event.OrderPlaceEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {
    JavaMailSender mailSender;

    public void sendOrderToEmail(OrderPlaceEvent order) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("manhte199x@gmail.com");
            helper.setTo("vanminh20022002@gamil.com");
            helper.setSubject("Đơn hàng bạn mơi đặt" + order.getOrderId());
            String body = "<p> Xin chào <b> " + order.getUserId() + "</b>,</p>"
                    + "<p> đơn hàng" + order.getOrderId() + "vừa được tạo thành công! </p>"
                    + "<p> với tổng số tiền là: <b>  " + order.getTotal() + "</b>,</p>";
            helper.setText(body, true);
            mailSender.send(message);
            System.out.println("Gửi email thành công");
        } catch (MessagingException e) {
            throw new RuntimeException("Gửi email thất bại ", e);
        }
    }

}
