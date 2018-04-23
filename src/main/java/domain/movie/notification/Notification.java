package domain.movie.notification;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
public class Notification {
    private final NotificationTo notificationTo;
    private final NotificationText notificationText;
}
