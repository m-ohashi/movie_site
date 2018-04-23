package domain.movie.user;

import domain.movie.notification.NotificationTo;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MailAddress {
    private final String value;

    public NotificationTo createNotificationTo() {
            return new NotificationTo(value);
    }
}
