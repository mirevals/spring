package model;

import java.time.LocalDateTime;

public class ActionLogEntry {
    private LocalDateTime timestamp;
    private String actorName;     // имя пользователя
    private String userRole;      // роль ("STUDENT", "TEACHER")
    private String action;        // название действия
    private String details;       // подробности

    public ActionLogEntry(LocalDateTime timestamp, String actorName, String userRole, String action, String details) {
        this.timestamp = timestamp;
        this.actorName = actorName;
        this.userRole = userRole;
        this.action = action;
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getActorName() {
        return actorName;
    }

    public String getUserRole() {
        return userRole;
    }

    public String getAction() {
        return action;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s): %s | %s",
                timestamp, actorName, userRole, action, details);
    }
}
