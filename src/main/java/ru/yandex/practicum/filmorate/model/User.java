package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String name;
    @PastOrPresent(message = "Марти, твой день рождения не может быть в будущем, машина времени снова неисправна!)")
    private LocalDate birthday;
    @NotNull(message = "Логин не должен быть пустым!")
    @NotBlank(message = "Логин не должен быть пустым!")
    private String login;
    @Email(message = "Почта должна быть как почта, а не то, что тут написано!")
    private String email;
    private Set<Integer> friends = new HashSet<>();
}
