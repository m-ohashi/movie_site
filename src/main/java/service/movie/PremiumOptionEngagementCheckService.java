package service.movie;

import domain.movie.introduction_code.IntroductionCode;
import domain.movie.new_premium_option_application.ImpossibleNewPremiumOptionApplyingError;
import domain.movie.new_premium_option_application.NewPremiumOptionApplyingUserEntity;
import domain.movie.new_premium_option_application.NewPremiumOptionApplyingUserRepository;
import domain.movie.premium_option.PremiumOption;
import domain.movie.premium_option.PremiumOptionRepository;
import domain.movie.user.UserId;
import io.vavr.control.Either;
import io.vavr.control.Option;

public class PremiumOptionEngagementCheckService {

    private NewPremiumOptionApplyingUserRepository newPremiumOptionApplyingUserRepository;
    private PremiumOptionRepository premiumOptionRepository;

    public Either<ImpossibleNewPremiumOptionApplyingError, NewPremiumOptionApplyingUserEntity> check(UserId userId, IntroductionCode introductionCode) {

        NewPremiumOptionApplyingUserEntity premiumOptionApplyingUserEntity = newPremiumOptionApplyingUserRepository.referByUserId(userId);

        if(!premiumOptionApplyingUserEntity.canEngagePremiumOption()) {
            return Either.left(ImpossibleNewPremiumOptionApplyingError.ONE_YEAR_NOT_PASSED_SINCE_REGISTER_DATE);
        }

        Option<PremiumOption> premiumOptionOption = premiumOptionRepository.refer(userId);

        if(premiumOptionOption.isDefined()) {
            return Either.left(ImpossibleNewPremiumOptionApplyingError.PREMIUM_OPTION_ALREADY_ENGAGED);
        }

        if(introductionCode.getIntroductionCodeUseState().isUnused()) {
            return Either.left(ImpossibleNewPremiumOptionApplyingError.INTRODUCTION_CODE_USED);
        }

        return Either.right(premiumOptionApplyingUserEntity);
    }
}
