package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Film {
    protected int id;
    @NotNull (message = "Название фильма не должно быть пустым!")
    @NotBlank (message = "Название фильма не должно быть пустым!")
    private String name;
    @Size(max=200, message = "Максимальный размер описания фильма не должен превышать 200 символов!")
    private String description;
    private LocalDate releaseDate;
    @Positive (message = "Продолжительность фильма должна быть положительной!")
    private int duration;
    private Set<Integer> likes = new HashSet<>();
}
