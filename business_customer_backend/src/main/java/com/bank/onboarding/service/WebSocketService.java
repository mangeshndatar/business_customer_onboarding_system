package com.bank.onboarding.service;

import com.bank.onboarding.dto.ApplicationEvent;
import com.bank.onboarding.dto.DashboardUpdate;
import com.bank.onboarding.dto.NotificationEvent;
import com.bank.onboarding.dto.WebSocketMessage;
import com.bank.onboarding.enums.ApplicationStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebSocketService {

    private SimpMessagingTemplate messagingTemplate;
    private SimpUserRegistry userRegistry;

    // Track active sessions
    private final Map<String, Set<String>> userSessions = new ConcurrentHashMap<>();
    private final Map<String, String> sessionToUser = new ConcurrentHashMap<>();

    /**
     * Notify all connected processing officers about new application
     */
    public void notifyProcessingOfficers(ApplicationEvent event) {
        // log.info("Notifying processing officers about new application: {}",
        // event.getApplicationId());

        WebSocketMessage message = WebSocketMessage.builder()
                .type("NEW_APPLICATION")
                .applicationId(event.getApplicationId())
                .data(event)
                .timestamp(LocalDateTime.now())
                .priority("HIGH")
                .build();

        // Send to all connected processing officers
        messagingTemplate.convertAndSend("/topic/applications/new", message);

        // Also update dashboard statistics
        updateDashboardStatistics(event.getStatus(), 1);

    }

    /**
     * Broadcast application status update
     */
    public void broadcastStatusUpdate(ApplicationEvent event) {
        // log.info("Broadcasting status update for application: {} - Status: {}",
        // event.getApplicationId(), event.getEventType());
        //
        WebSocketMessage message = WebSocketMessage.builder()
                .type("STATUS_UPDATE")
                .applicationId(event.getApplicationId())
                .build();

        // Send to topic for all officers
        messagingTemplate.convertAndSend("/topic/applications/updates", message);

        // Send to specific application channel for subscribed users
        messagingTemplate.convertAndSend("/topic/applications/" + event.getApplicationId(), message);

        // Update dashboard
        updateDashboardStatistics(event.getStatus(), 0);
    }

    /**
     * Send in-app notification to specific user
     */
    public void sendInAppNotification(NotificationEvent notification) {
        // log.info("Sending in-app notification to: {}",
        // notification.getRecipientEmail());

        WebSocketMessage message = WebSocketMessage.builder()
                .type("NOTIFICATION")
                .applicationId(notification.getApplicationId())
                .data(notification)
                .timestamp(LocalDateTime.now())
                .priority("NORMAL")
                .build();

        // Send to user-specific queue
        messagingTemplate.convertAndSendToUser(
                notification.getRecipientEmail(),
                "/queue/notifications",
                message);

        // Also send to a general notifications topic for monitoring
        messagingTemplate.convertAndSend("/topic/notifications/all", message);
    }

    /**
     * Send real-time dashboard update
     */
    public void updateDashboard(DashboardUpdate update) {
        // log.info("Updating dashboard with: {}", update.getUpdateType());

        messagingTemplate.convertAndSend("/topic/dashboard/updates", update);
    }

    /**
     * Update dashboard statistics
     */
    private void updateDashboardStatistics(ApplicationStatus eventType, int newApplications) {
        Map<String, Object> statistics = new HashMap<>();
        statistics.put("timestamp", LocalDateTime.now());
        statistics.put("eventType", eventType);

        if (newApplications > 0) {
            statistics.put("newApplications", newApplications);
        }

        // Calculate current stats (in real app, would query from database)
        statistics.put("totalPending", getRandomStat());
        statistics.put("totalApproved", getRandomStat());
        statistics.put("totalRejected", getRandomStat());

        DashboardUpdate update = DashboardUpdate.builder()
                .updateType("STATISTICS")
                .data(statistics)
                .timestamp(LocalDateTime.now())
                .build();

        messagingTemplate.convertAndSend("/topic/dashboard/statistics", update);
    }

    /**
     * Send alert to all officers
     */
    public void sendAlert(String alertType, String message, String severity) {
        WebSocketMessage alert = WebSocketMessage.builder()
                .type("ALERT")
                .data(Map.of(
                        "alertType", alertType,
                        "message", message,
                        "severity", severity))
                .timestamp(LocalDateTime.now())
                .priority("HIGH")
                .build();

        messagingTemplate.convertAndSend("/topic/alerts", alert);
    }

    /**
     * Send personalized message to specific officer
     */
    public void sendToOfficer(String officerId, Object message) {
        messagingTemplate.convertAndSendToUser(officerId, "/queue/personal", message);
    }

    /**
     * Broadcast system message
     */
    public void broadcastSystemMessage(String message, String type) {
        WebSocketMessage systemMessage = WebSocketMessage.builder()
                .type("SYSTEM")
                .data(Map.of(
                        "message", message,
                        "type", type))
                .timestamp(LocalDateTime.now())
                .priority("LOW")
                .build();

        messagingTemplate.convertAndSend("/topic/system", systemMessage);
    }

    /**
     * Get list of connected users
     */
    public List<String> getConnectedUsers() {
        return userRegistry.getUsers().stream()
                .map(SimpUser::getName)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * Check if user is connected
     */
    public boolean isUserConnected(String username) {
        return userRegistry.getUser(username) != null;
    }

    /**
     * Helper method to generate random statistics (replace with actual DB query)
     */
    private int getRandomStat() {
        return new Random().nextInt(100);
    }
}
