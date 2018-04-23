package domain.movie.user;

import lombok.AllArgsConstructor;

import java.time.LocalDate;


@AllArgsConstructor
public class RegistrationDate {
    private final LocalDate value;

    public boolean isOneYearPassedSinceRegisterDate() {
        return LocalDate.now().isAfter(value.plusYears(1));
    }
}
