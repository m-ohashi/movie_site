package domain.movie.new_premium_option_application;

import domain.movie.notification.NotificationText;
import domain.movie.premium_option.PremiumOptionId;
import domain.movie.user.UserId;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NewPremiumOptionApplyingNotificationText {
    private final UserId userId;
    private final PremiumOptionId premiumOptionId;

    public NotificationText createNotificationText() {
        return new NotificationText(
                "ようこそ" + userId.getValue() + "さん、契約したオプション番号は、" + premiumOptionId.getValue() + "です"
        );
    }

}
