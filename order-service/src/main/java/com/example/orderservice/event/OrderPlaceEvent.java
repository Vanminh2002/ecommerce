package com.example.orderservice.event;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderPlaceEvent {
    Long orderId;
    Long userId;
    Double total;
}
