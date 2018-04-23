package domain.movie.user

import domain.movie.notification.NotificationTo
import spock.lang.Specification

class PhoneNumberTest extends Specification {
    def "CreateNotificationTo"() {
        setup:
        String number = "09012345678"
        PhoneNumber phoneNumber = new PhoneNumber(number)

        expect:
        phoneNumber.createNotificationTo() == new NotificationTo(number)
    }
}
