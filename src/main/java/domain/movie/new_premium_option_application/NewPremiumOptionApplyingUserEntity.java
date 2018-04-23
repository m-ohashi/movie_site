package domain.movie.new_premium_option_application;

import domain.movie.notification.Notification;
import domain.movie.notification.NotificationTo;
import domain.movie.premium_option.PremiumOptionId;
import domain.movie.user.ContactAddress;
import domain.movie.user.RegistrationDate;
import domain.movie.user.UserId;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NewPremiumOptionApplyingUserEntity {
    private final UserId userId;
    private final RegistrationDate registrationDate;
    private final ContactAddress contactAddress;

    public boolean canEngagePremiumOption() {
        return registrationDate.isOneYearPassedSinceRegisterDate();
    }

    //本文が作れない
    public Notification createNotification(PremiumOptionId premiumOptionId) {
        NewPremiumOptionApplyingNotificationText newPremiumOptionApplyingNotificationText
                = new NewPremiumOptionApplyingNotificationText(userId, premiumOptionId);

        return new Notification(
                createNotificationTo(),
                newPremiumOptionApplyingNotificationText.createNotificationText()
        );
    }

    private NotificationTo createNotificationTo() {
        return contactAddress.createNotificationTo();
    }

}
