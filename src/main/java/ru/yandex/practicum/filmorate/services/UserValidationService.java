package ru.yandex.practicum.filmorate.services;

import org.slf4j.Logger;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;
//Оставил только кастомные проверки
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
        } else if (user.getLogin().equals("") || user.getLogin().contains(" ")) {
            log.error("Логин не может быть пустым или содержать пробелы!, {}", user);
            throw new ValidationException("Логин не может быть пустым или содержать пробелы!");
        }
    }
}
