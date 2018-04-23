package domain.movie.new_premium_option_application;

import domain.movie.user.UserId;

public interface NewPremiumOptionApplyingUserRepository {
    public NewPremiumOptionApplyingUserEntity referByUserId(UserId userId);
}
