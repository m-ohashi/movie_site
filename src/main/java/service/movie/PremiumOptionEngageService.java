package service.movie;

import domain.movie.introduction_code.IntroductionCode;
import domain.movie.new_premium_option_application.NewPremiumOptionApplyingUserEntity;
import domain.movie.premium_option.PremiumOptionId;
import domain.movie.premium_option.PremiumOptionRepository;
import domain.movie.user.UserId;

import java.util.function.Supplier;

public class PremiumOptionEngageService {
    private PremiumOptionEngagementCheckService premiumOptionEngagementCheckService;
    private PremiumOptionRepository premiumOptionRepository;
    private NoticeService noticeService;

    public void engage(UserId userId, IntroductionCode introductionCode) {

        NewPremiumOptionApplyingUserEntity entity = premiumOptionEngagementCheckService.check(userId, introductionCode)
                .getOrElseThrow((Supplier<RuntimeException>) RuntimeException::new);

        PremiumOptionId premiumOptionid = premiumOptionRepository.engage(userId);
        //インパラが作れない
        noticeService.send(entity.createNotification(premiumOptionid));
    }
}
