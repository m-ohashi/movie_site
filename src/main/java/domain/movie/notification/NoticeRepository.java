package domain.movie.notification;

public interface NoticeRepository {
    public void sendNotification(Notification notification);
}
