package ru.yandex.practicum.filmorate.storage.InMemory.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.interfaces.UserStorageIm;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class InMemoryUserStorage implements UserStorageIm {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final Map<Long, User> users = new HashMap<>();
    private long idGen = 1;


    @Autowired
    public InMemoryUserStorage() {
    }

    //Получаем конкретного пользователя
    @Override
    public User getUser(Long id) {
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

    //Добавляем пользователя
    @Override
    public User addNewUser(User user) {
        user.setId(idGen);
        users.put(idGen, user);
        idGen++;
        log.info("Добавлен новый пользователь, {}", user);
        return user;
    }

    //Обновляем пользователя
    @Override
    public User updateNewUser(User user) {
        getUser(user.getId());
        users.put(user.getId(), user);
        log.info("Пользователь обновлен - , {}", user);
        return user;
    }

}
