package domain.movie.user;

import domain.movie.notification.NotificationTo;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Sms implements ContactAddress {
    private final PhoneNumber phoneNumber;

    public NotificationTo createNotificationTo() {
        return phoneNumber.createNotificationTo();
    }
}
