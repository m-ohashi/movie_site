package domain.movie.user

import domain.movie.notification.NotificationTo
import spock.lang.Specification

class MailAddressTest extends Specification {

    def "CreateNotificationTo"() {
        setup:
        String address = "hoge@foo.com"
        MailAddress mailAddress = new MailAddress(address)
        expect:
        mailAddress.createNotificationTo() == new NotificationTo(address)
    }
}
