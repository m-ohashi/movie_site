package service.movie

import domain.movie.introduction_code.IntroductionCode
import domain.movie.introduction_code.PremiumOptionIntroductionCode
import domain.movie.new_premium_option_application.ImpossibleNewPremiumOptionApplyingError
import domain.movie.new_premium_option_application.NewPremiumOptionApplyingUserEntity
import domain.movie.new_premium_option_application.NewPremiumOptionApplyingUserRepository
import domain.movie.notification.Notification
import domain.movie.premium_option.PremiumOptionRepository
import domain.movie.user.Mail
import domain.movie.user.MailAddress
import domain.movie.user.RegistrationDate
import domain.movie.user.UserId
import io.vavr.control.Either
import spock.lang.Specification

import java.time.LocalDate

import static domain.movie.introduction_code.IntroductionCodeUseState.UNUSED
import static domain.movie.new_premium_option_application.ImpossibleNewPremiumOptionApplyingError.ONE_YEAR_NOT_PASSED_SINCE_REGISTER_DATE
import static io.vavr.control.Either.left
import static io.vavr.control.Either.right

class PremiumOptionEngageServiceTest extends Specification {

    private static final UserId USER_ID = new UserId("1")

    private static final IntroductionCode INTRODUCTION_CODE = new IntroductionCode(
            new PremiumOptionIntroductionCode("1"),
            UNUSED
    )

    def "canEngage"() {
        setup:
        PremiumOptionRepository premiumOptionRepository = new PremiumOptionEngagedRepositoryMock()
        PremiumOptionEngageService service = new PremiumOptionEngageService(
                new PremiumOptionEngagementCheckServiceMock(
                        new NewPremiumOptionApplyingUserRepositoryMock(),
                        premiumOptionRepository
                ),
                premiumOptionRepository,
                new NoticeServiceMock()
        )

        expect:
        service.engage(USER_ID, INTRODUCTION_CODE)
        PremiumOptionEngagedRepositoryMock.count == 1
        NoticeServiceMock.count == 1
    }

    def "canNotEngageForRegisterDateError"() {
        setup:
        PremiumOptionRepository premiumOptionRepository = new PremiumOptionEngagedRepositoryMock()
        PremiumOptionEngageService service = new PremiumOptionEngageService(
                new PremiumOptionEngagementCheckServiceRegisterDateErrorMock(
                        new NewPremiumOptionApplyingUserRepositoryMock(),
                        premiumOptionRepository
                ),
                premiumOptionRepository,
                new NoticeServiceMock()
        )

        when:
        service.engage(USER_ID, INTRODUCTION_CODE)

        then:
        thrown(RuntimeException.class)
        PremiumOptionEngagedRepositoryMock.count == 0
        NoticeServiceMock.count == 0
    }
}

class PremiumOptionEngagementCheckServiceMock extends PremiumOptionEngagementCheckService {

    PremiumOptionEngagementCheckServiceMock(NewPremiumOptionApplyingUserRepository newPremiumOptionApplyingUserRepository, PremiumOptionRepository premiumOptionRepository) {
        super(newPremiumOptionApplyingUserRepository, premiumOptionRepository)
    }

    public Either<ImpossibleNewPremiumOptionApplyingError, NewPremiumOptionApplyingUserEntity> check(UserId userId, IntroductionCode introductionCode) {
        right(new NewPremiumOptionApplyingUserEntity(
                new UserId("1"),
                new RegistrationDate(LocalDate.of(2017, 01, 01)),
                new Mail(new MailAddress("hoge@foo.com"))

        ))
    }
}

class PremiumOptionEngagementCheckServiceRegisterDateErrorMock extends PremiumOptionEngagementCheckService {

    PremiumOptionEngagementCheckServiceRegisterDateErrorMock(NewPremiumOptionApplyingUserRepository newPremiumOptionApplyingUserRepository, PremiumOptionRepository premiumOptionRepository) {
        super(newPremiumOptionApplyingUserRepository, premiumOptionRepository)
    }

    public Either<ImpossibleNewPremiumOptionApplyingError, NewPremiumOptionApplyingUserEntity> check(UserId userId, IntroductionCode introductionCode) {
        left(ONE_YEAR_NOT_PASSED_SINCE_REGISTER_DATE)
    }
}

class NoticeServiceMock extends NoticeService {

    public static int count = 0

    NoticeServiceMock() {
        super(new NoticeRepositoryMock())
    }

    public void send(Notification notification) {
        count = ++count
    }
}
