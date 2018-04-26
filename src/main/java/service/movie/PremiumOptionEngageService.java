package service.movie;

import domain.movie.introduction_code.IntroductionCode;
import domain.movie.new_premium_option_application.NewPremiumOptionApplyingUserEntity;
import domain.movie.premium_option.PremiumOptionId;
import domain.movie.premium_option.PremiumOptionRepository;
import domain.movie.user.UserId;
import lombok.AllArgsConstructor;

import java.util.function.Supplier;

@AllArgsConstructor
public class PremiumOptionEngageService {
    private final PremiumOptionEngagementCheckService premiumOptionEngagementCheckService;
    private final PremiumOptionRepository premiumOptionRepository;
    private final NoticeService noticeService;

    public void engage(UserId userId, IntroductionCode introductionCode) {

        NewPremiumOptionApplyingUserEntity entity = premiumOptionEngagementCheckService.check(userId, introductionCode)
                .getOrElseThrow((Supplier<RuntimeException>) RuntimeException::new);

        PremiumOptionId premiumOptionid = premiumOptionRepository.engage(userId);

        noticeService.send(entity.createNotification(premiumOptionid));
    }
}
