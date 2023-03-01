package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.HashSet;

@Data
@Builder
public class User {
    @PositiveOrZero(message = "ID пользователя не может быть отрицательным числом")
    private long id;
    private String name;
    @PastOrPresent(message = "Марти, твой день рождения не может быть в будущем, машина времени снова неисправна!)")
    private LocalDate birthday;
    @NotBlank(message = "Логин не должен быть пустым!")
    private String login;
    @Email(message = "Почта должна быть как почта, а не то, что тут написано!")
    private String email;
    private HashSet<Long> friends;
}
