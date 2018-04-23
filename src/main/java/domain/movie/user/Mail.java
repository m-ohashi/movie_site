package domain.movie.user;

import domain.movie.notification.NotificationTo;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Mail implements ContactAddress {
    private final MailAddress mailAddress;

    public NotificationTo createNotificationTo() {
        return mailAddress.createNotificationTo();
    }
}
