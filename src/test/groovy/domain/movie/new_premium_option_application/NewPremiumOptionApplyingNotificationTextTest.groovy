package domain.movie.new_premium_option_application

import domain.movie.notification.NotificationText
import domain.movie.premium_option.PremiumOptionId
import domain.movie.user.UserId
import spock.lang.Specification

class NewPremiumOptionApplyingNotificationTextTest extends Specification {
    def "CreateNotificationText"() {
        setup:
        NewPremiumOptionApplyingNotificationText text = new NewPremiumOptionApplyingNotificationText(
                new UserId("abc12345"),
                new PremiumOptionId("1")
        )

        expect:
         text.createNotificationText() == new NotificationText("ようこそabc12345さん、契約したオプション番号は、1です")

    }
}
