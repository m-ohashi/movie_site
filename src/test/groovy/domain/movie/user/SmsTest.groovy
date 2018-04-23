package domain.movie.user

import domain.movie.notification.NotificationTo
import spock.lang.Specification

class SmsTest extends Specification {
    def "CreateNotificationTo"() {
        setup:
        String number = "09012345678"
        PhoneNumber phoneNumber = new PhoneNumber(number)
        Sms sms = new Sms(phoneNumber)

        expect:
        sms.createNotificationTo() == new NotificationTo(number)
    }
}
