package ru.yandex.practicum.filmorate.services;

import org.slf4j.Logger;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Map;

public class UserValidationService {

    public void checkPutUserValidate(Logger log, Map<Integer, User> users, User user) {
        if (!users.containsKey(user.getId())) {
            log.error("Такого пользователя не существует!, {}", user);
            throw new ValidationException("Такого пользователя не существует!");
        }
    }

    public void checkPostUserValidate(Logger log, Map<Integer, User> users, User user) {
        if (users.containsKey(user.getId())) {
            log.error("Такой пользователь уже существует!, {}", user);
            throw new ValidationException("Такой пользователь уже существует!");
        } else if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.error("Электронная почта не может быть пустой и должна содержать символ - @, {}", user);
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ - @");
        } else if (user.getLogin().equals("") || user.getLogin().contains(" ")) {
            log.error("Логин не может быть пустым и содержать пробелы!, {}", user);
            throw new ValidationException("Логин не может быть пустым и содержать пробелы!");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Дата рождения не может быть в будущем!, {}", user);
            throw new ValidationException("Дата рождения не может быть в будущем!");
        }
    }
}
