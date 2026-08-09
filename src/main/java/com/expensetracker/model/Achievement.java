package com.expensetracker.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Achievement implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int userId;
    private String badgeKey;
    private String badgeName;
    private String description;
    private String iconClass;
    private Timestamp unlockedAt;

    public Achievement() {}

    public Achievement(int userId, String badgeKey, String badgeName, String description, String iconClass) {
        this.userId = userId;
        this.badgeKey = badgeKey;
        this.badgeName = badgeName;
        this.description = description;
        this.iconClass = iconClass;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getBadgeKey() { return badgeKey; }
    public void setBadgeKey(String badgeKey) { this.badgeKey = badgeKey; }

    public String getBadgeName() { return badgeName; }
    public void setBadgeName(String badgeName) { this.badgeName = badgeName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getIconClass() { return iconClass; }
    public void setIconClass(String iconClass) { this.iconClass = iconClass; }

    public Timestamp getUnlockedAt() { return unlockedAt; }
    public void setUnlockedAt(Timestamp unlockedAt) { this.unlockedAt = unlockedAt; }
}
