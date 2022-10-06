package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class User {
    private int id;


    private String name;

    private LocalDate birthday;

    @NotNull
    @NotBlank
    private String login;

    @NotNull
    @NotBlank
    @Email
    private String email;


}
