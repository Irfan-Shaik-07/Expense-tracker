package com.expensetracker.dao;

import com.expensetracker.model.Notification;
import java.util.List;

public interface NotificationDAO {
    Notification createNotification(Notification notification);
    List<Notification> getNotificationsByUser(int userId);
    boolean markAsRead(int notificationId, int userId);
    boolean markAllAsRead(int userId);
}
