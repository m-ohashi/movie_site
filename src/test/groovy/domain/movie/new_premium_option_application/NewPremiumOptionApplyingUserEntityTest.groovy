package domain.movie.new_premium_option_application

import domain.movie.notification.Notification
import domain.movie.notification.NotificationText
import domain.movie.notification.NotificationTo
import domain.movie.premium_option.PremiumOptionId
import domain.movie.user.*
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

@Unroll
class NewPremiumOptionApplyingUserEntityTest extends Specification {
    UserId userId = new UserId("abc12345")
    ContactAddress contactAddress = new Mail(new MailAddress("hoge@foo.com"))


    def "CanEngagePremiumOption"() {
        setup:
        RegistrationDate registrationDate = new RegistrationDate(date)
        NewPremiumOptionApplyingUserEntity entity = new NewPremiumOptionApplyingUserEntity(userId, registrationDate, contactAddress)

        expect:
        entity.canEngagePremiumOption() == expected

        where:
        date                       || expected
        LocalDate.of(2017, 01, 01) || true
        LocalDate.of(2018, 01, 01) || false
    }

    def "CreateNotification"() {
        setup:
        RegistrationDate registrationDate = new RegistrationDate(LocalDate.of(2017, 01, 01))
        NewPremiumOptionApplyingUserEntity entity = new NewPremiumOptionApplyingUserEntity(userId, registrationDate, contactAddress)
        PremiumOptionId premiumOptionId = new PremiumOptionId("1")

        expect:
        entity.createNotification(premiumOptionId) == new Notification(
                new NotificationTo("abc12345"),
                new NotificationText(
                        "ようこそabc12345さん、契約したオプション番号は、1です"
                )
        )
    }
}
