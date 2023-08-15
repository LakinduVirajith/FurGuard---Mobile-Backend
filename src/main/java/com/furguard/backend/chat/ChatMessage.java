package com.furguard.backend.chat;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {
    private String content;
    private String sender;
    private double trackerLatitude;
    private double trackerLongitude;
    private double distance;
    private LocalDateTime lastTrackingTime;
    private MessageType type;
}

