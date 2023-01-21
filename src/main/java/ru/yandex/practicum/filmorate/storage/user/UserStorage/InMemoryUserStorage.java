package ru.yandex.practicum.filmorate.storage.user.UserStorage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.interfaces.UserStorage;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.services.validationServices.UserValidationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final UserValidationService userValidateService;
    private final Map<Integer, User> users = new HashMap<>();
    private int idGen = 1;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);


    @Autowired
    public InMemoryUserStorage(UserValidationService userValidateService) {
        this.userValidateService = userValidateService;
    }

    //Получаем конкретного пользователя
    @Override
    public User getUser(Integer id) {
        userValidateService.checkGetUserValidate(log, users, id);
        return users.get(id);
    }


    //Получаем список всех пользователей по запросу
    @Override
    public ArrayList<User> getAllUsers() {
        log.debug("Количество пользователей всего: {}", users.size());
        return new ArrayList<>(users.values());
    }

    //Получаем мапу с пользователями в чистом виде
    @Override
    public Map<Integer, User> getUsers() {
        return users;
    }

    //Добавляем пользователя по запросу, проверяя, проходит ли он по параметрам
    @Override
    public User addNewUser(User user) {
        userValidateService.checkPostUserValidate(log, users, user);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(idGen);
        users.put(idGen, user);
        idGen++;
        log.info("Добавлен новый пользователь, {}", user);
        return user;
    }

    //Обновляем пользователя по запросу, проверив, есть ли такой пользователь, которого хотим обновить
    @Override
    public User updateNewUser(User user) {
        userValidateService.checkGetUserValidate(log, users, user.getId());
        if (user.getName().trim().equals("")) {
            user.setName(user.getLogin());
        }
        user.setId(user.getId());
        users.put(user.getId(), user);
        log.info("Пользователь обновлен - , {}", user);
        return user;
    }

}
