package service.movie

import domain.movie.introduction_code.IntroductionCode
import domain.movie.introduction_code.IntroductionCodeUseState
import domain.movie.introduction_code.PremiumOptionIntroductionCode
import domain.movie.new_premium_option_application.NewPremiumOptionApplyingUserEntity
import domain.movie.new_premium_option_application.NewPremiumOptionApplyingUserRepository
import domain.movie.premium_option.PremiumOption
import domain.movie.premium_option.PremiumOptionId
import domain.movie.premium_option.PremiumOptionRepository
import domain.movie.user.Mail
import domain.movie.user.MailAddress
import domain.movie.user.RegistrationDate
import domain.movie.user.UserId
import io.vavr.control.Option
import spock.lang.Specification

import java.time.LocalDate

import static domain.movie.new_premium_option_application.ImpossibleNewPremiumOptionApplyingError.*
import static io.vavr.control.Either.left

class PremiumOptionEngagementCheckServiceTest extends Specification {

    def "申込日から１年経っていないことをチェックできる"() {
        setup:
        PremiumOptionEngagementCheckService service = new PremiumOptionEngagementCheckService(
                new NewPremiumOptionApplyingUserOneYearNotPassedSinceRegisterDateRepositoryMock(),
                new PremiumOptionRepositoryMock()
        )

        expect:
        UserId userId = new UserId("abc12345")
        IntroductionCode introductionCode = new IntroductionCode(new PremiumOptionIntroductionCode("1"), IntroductionCodeUseState.UNUSED)
        service.check(userId, introductionCode) == left(ONE_YEAR_NOT_PASSED_SINCE_REGISTER_DATE)
    }

    def "プレミアムオプションをすでに契約していることをチェックできる"() {
        setup:
        PremiumOptionEngagementCheckService service = new PremiumOptionEngagementCheckService(
                new NewPremiumOptionApplyingUserRepositoryMock(),
                new PremiumOptionEngagedRepositoryMock()
        )

        expect:
        UserId userId = new UserId("abc12345")
        IntroductionCode introductionCode = new IntroductionCode(new PremiumOptionIntroductionCode("1"), IntroductionCodeUseState.UNUSED)
        service.check(userId, introductionCode) == left(PREMIUM_OPTION_ALREADY_ENGAGED)

    }

    def "紹介コードが使用済みであることがチェックできる"() {
        setup:
        PremiumOptionEngagementCheckService service = new PremiumOptionEngagementCheckService(
                new NewPremiumOptionApplyingUserRepositoryMock(),
                new PremiumOptionRepositoryMock()
        )

        expect:
        UserId userId = new UserId("abc12345")
        IntroductionCode introductionCode = new IntroductionCode(new PremiumOptionIntroductionCode("1"), IntroductionCodeUseState.USE)
        service.check(userId, introductionCode) == left(INTRODUCTION_CODE_USED)
    }

}

class NewPremiumOptionApplyingUserOneYearNotPassedSinceRegisterDateRepositoryMock implements NewPremiumOptionApplyingUserRepository {

    @Override
    NewPremiumOptionApplyingUserEntity referByUserId(UserId userId) {
        new NewPremiumOptionApplyingUserEntity(
                userId,
                new RegistrationDate(LocalDate.of(2018, 01, 01)),
                new Mail(new MailAddress("hoge@foo.com"))
        )
    }
}

class NewPremiumOptionApplyingUserRepositoryMock implements NewPremiumOptionApplyingUserRepository {
    @Override
    NewPremiumOptionApplyingUserEntity referByUserId(UserId userId) {
        new NewPremiumOptionApplyingUserEntity(
                userId,
                new RegistrationDate(LocalDate.of(2017, 01, 01)),
                new Mail(new MailAddress("hoge@foo.com"))
        )
    }
}

class PremiumOptionEngagedRepositoryMock implements PremiumOptionRepository {

    public static int count = 0

    @Override
    PremiumOptionId engage(UserId userId) {
        count = ++count
        new PremiumOptionId("1")
    }

    @Override
    Option<PremiumOption> refer(UserId userId) {
        Option.of(new PremiumOption(new PremiumOptionId("1")))
    }
}

class PremiumOptionRepositoryMock implements PremiumOptionRepository {

    @Override
    PremiumOptionId engage(UserId userId) {
        new PremiumOptionId("1")
    }

    @Override
    Option<PremiumOption> refer(UserId userId) {
        Option.none()
    }
}
