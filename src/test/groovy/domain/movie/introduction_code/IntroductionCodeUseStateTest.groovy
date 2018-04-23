package domain.movie.introduction_code

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class IntroductionCodeUseStateTest extends Specification {
    def "IsUnused"() {
        expect:
        input.isUnused() == expected

        where:
        input                           || expected
        IntroductionCodeUseState.USE    || false
        IntroductionCodeUseState.UNUSED || true
    }
}
