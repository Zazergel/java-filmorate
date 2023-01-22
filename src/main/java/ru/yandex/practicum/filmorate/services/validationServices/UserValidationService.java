package ru.yandex.practicum.filmorate.services.validationServices;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.interfaces.UserStorage;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;

@Component
public class UserValidationService {

    //Проверка добавления нового пользователя в соответствии с требованиями
    public void checkPostUserValidate(Logger log, Map<Integer, User> users, User user) {
        if (users.containsKey(user.getId())) {
            log.error("Такой пользователь уже существует!, {}", user);
            throw new ValidationException("Такой пользователь уже существует!");
        } else if (user.getLogin().equals("") || user.getLogin().contains(" ")) {
            log.error("Логин не может быть пустым или содержать пробелы!, {}", user);
            throw new ValidationException("Логин не может быть пустым или содержать пробелы!");
        }
    }

    //Проверка наличия пользователя по запросу
    public void checkGetUserValidate(Logger log, Map<Integer, User> users, Integer id) {
        if (!users.containsKey(id)) {
            log.error("Такого пользователя не существует!, {}", id);
            throw new NotFoundException("Такого пользователя не существует!");
        }
    }

    //Проверяем удален ли пользователь из друзей
    public void checkRemoveFriendValidate(Logger log, UserStorage userStorage, Integer id, Integer friendId) {
        if (!userStorage.getUser(id).getFriends().contains(friendId)) {
            log.error("Пользователи с id:" + id + " и id:" + friendId + " - не друзья");
            throw new ValidationException("Этого пользователя уже нет в друзьях");
        }
    }

    //Проверка имени пользователя на пустоту, если это так, то заполняем поле имени - логином.
    public void checkUserNameForBlankOrNull(Logger log, User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.info("Имя пользователя изменено на его логин, т.к. оно было пустым.");
        }
    }

    //На дубликаты друзей мы не проверяем, тк коллекция СЕТ сама по себе не допускает дубликатов

}
