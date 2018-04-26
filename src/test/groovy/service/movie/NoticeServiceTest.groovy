package service.movie

import domain.movie.notification.NoticeRepository
import domain.movie.notification.Notification
import domain.movie.notification.NotificationText
import domain.movie.notification.NotificationTo
import spock.lang.Specification

class NoticeServiceTest extends Specification {
    def "Send"() {
        setup:
        NoticeService service = new NoticeService(new NoticeRepositoryMock())

        expect:
        service.send(new Notification(new NotificationTo("hoge@foo.com"),new NotificationText("こんにちは")))
        NoticeRepositoryMock.count == 1
    }
}
class NoticeRepositoryMock implements NoticeRepository {
    public static int count = 0

    @Override
    void sendNotification(Notification notification) {
        count = ++count
    }
}
