package service.movie;

import domain.movie.notification.NoticeRepository;
import domain.movie.notification.Notification;

public class NoticeService {
    private NoticeRepository noticeRepository;

    public void send(Notification notification) {

        noticeRepository.sendNotification(notification);
    }
}
