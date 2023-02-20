package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Data
@Builder
public class Film {
    private final HashSet<Long> likes;
    private final List<Genre> genres;
    @PositiveOrZero(message = "Id фильма не может быть отрицательным числом!")
    private long id;
    @NotBlank(message = "Название фильма не должно быть пустым!")
    private String name;
    @Size(max = 200, message = "Максимальный размер описания фильма не должен превышать 200 символов!")
    private String description;
    @NotNull
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительной!")
    private int duration;
    @NotNull(message = "Mpa не может быть пустым!")
    private Mpa mpa;
    private int rate;
}
