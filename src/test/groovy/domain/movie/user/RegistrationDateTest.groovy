package domain.movie.user

import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

@Unroll
class RegistrationDateTest extends Specification {
    def "IsOneYearPassedSinceRegisterDate"() {
        setup:
        RegistrationDate registrationDate = new RegistrationDate(date)

        expect:
        registrationDate.oneYearPassedSinceRegisterDate == expected

        where:
        date                       || expected
        LocalDate.of(2017, 01, 01) || true
        LocalDate.of(2018, 01, 01) || false
    }
}
