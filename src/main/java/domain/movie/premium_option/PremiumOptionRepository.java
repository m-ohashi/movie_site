package domain.movie.premium_option;

import domain.movie.user.UserId;
import io.vavr.control.Option;

public interface PremiumOptionRepository {
    public PremiumOptionId engage(UserId userId);

    public Option<PremiumOption> refer(UserId userId);
}
