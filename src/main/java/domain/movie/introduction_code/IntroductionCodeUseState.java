package domain.movie.introduction_code;

public enum IntroductionCodeUseState {
    USE,
    UNUSED;

    public boolean isUnused() {
        return this.equals(UNUSED);
    }
}
