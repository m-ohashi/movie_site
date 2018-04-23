package domain.movie.user

import domain.movie.notification.NotificationTo
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class MailTest extends Specification {
    def "CreateNotificationTo"() {
        setup:
        String address = "hoge@foo.com"
        MailAddress mailAddress = new MailAddress(address)
        Mail mail = new Mail(mailAddress)

        expect:
        mail.createNotificationTo() == new NotificationTo(address)
    }
}
