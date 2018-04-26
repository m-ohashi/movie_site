package service.movie;

import domain.movie.notification.NoticeRepository;
import domain.movie.notification.Notification;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public void send(Notification notification) {

        noticeRepository.sendNotification(notification);
    }
}
