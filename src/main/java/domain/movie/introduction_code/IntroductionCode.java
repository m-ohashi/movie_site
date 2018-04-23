package domain.movie.introduction_code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class IntroductionCode {
    private final PremiumOptionIntroductionCode premiumOptionIntroductionCode;
    @Getter
    private final IntroductionCodeUseState introductionCodeUseState;
}
