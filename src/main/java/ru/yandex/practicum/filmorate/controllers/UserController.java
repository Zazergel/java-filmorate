package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();

    private int idGen = 1;

    @GetMapping
    public Collection<User> getUsers() {
        log.debug("Количество пользователей всего: {}", users.size());
        return users.values();
    }

    @PostMapping
    public User addNewUser(@Valid @RequestBody User user) {
        if (user.getLogin() == null || user.getLogin().isBlank() || containsWhiteSpace(user.getLogin())) {
            throw new ValidationException("Логин не может быть пустым или содержать пробелы!");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Марти, ты опять напортачил с машиной времени! " +
                    "Твой день рождения не может быть в будущем!");
        }
        user.setId(idGen++);
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User updateNewUser(@Valid @RequestBody User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        } else {
            throw new ValidationException("Нет пользователя с id=" + user.getId());
        }

        return users.get(user.getId());
    }

    public static boolean containsWhiteSpace(final String login) {
        if (login != null) {
            for (int i = 0; i < login.length(); i++) {
                if (Character.isWhitespace(login.charAt(i))) {
                    return true;
                }
            }
        }
        return false;
    }
}
