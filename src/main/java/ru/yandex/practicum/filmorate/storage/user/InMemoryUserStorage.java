package ru.yandex.practicum.filmorate.storage.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.interfaces.UserStorage;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.services.validationServices.UserValidationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.yandex.practicum.filmorate.services.user.UserService.checkUserNameForBlankOrNull;

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
        if (!users.containsKey(id)) {
            log.error("Такого пользователя не существует!, {}", id);
            throw new NotFoundException("Такого пользователя не существует!");
        }
        return users.get(id);
    }

    //Получаем список всех пользователей по запросу
    @Override
    public List<User> getAllUsers() {
        log.debug("Количество пользователей всего: {}", users.size());
        return new ArrayList<>(users.values());
    }

    //Добавляем пользователя по запросу, проверяя, проходит ли он по параметрам
    @Override
    public User addNewUser(User user) {
        userValidateService.checkPostUserValidate(log, users, user);
        checkUserNameForBlankOrNull(log, user);
        user.setId(idGen);
        users.put(idGen, user);
        idGen++;
        log.info("Добавлен новый пользователь, {}", user);
        return user;
    }

    //Обновляем пользователя по запросу, проверив, есть ли такой пользователь, которого хотим обновить
    @Override
    public User updateNewUser(User user) {
        if (!users.containsKey(user.getId())) {
            log.error("Такого пользователя не существует!, {}", user.getId());
            throw new NotFoundException("Такого пользователя не существует!");
        }
        checkUserNameForBlankOrNull(log, user);
        users.put(user.getId(), user);
        log.info("Пользователь обновлен - , {}", user);
        return user;
    }

}
