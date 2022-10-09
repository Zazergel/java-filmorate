package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.services.UserValidationService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/users")
public class UserController {
    private final UserValidationService userValidateService = new UserValidationService();
    private final Map<Integer, User> users = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private int idGen = 1;

    @GetMapping
    public ArrayList<User> getUsers() {
        log.debug("Количество пользователей всего: {}", users.size());
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User addNewUser(@Valid @RequestBody User user) {
        userValidateService.checkPostUserValidate(log, users, user);
        if (user.getName() == null) {
            user.setName(user.getLogin());
            user.setId(idGen);
            users.put(idGen, user);
            idGen++;
            log.info("Добавлен новый пользователь, {}", user);
            return user;
        } else {
            user.setId(idGen);
            users.put(idGen, user);
            idGen++;
            log.info("Добавлен новый пользователь, {}", user);
            return user;
        }
    }

    @PutMapping
    public User updateNewUser(@Valid @RequestBody User user) {
        userValidateService.checkPutUserValidate(log, users, user);
        if (user.getName().trim().equals("")) {
            user.setName(user.getLogin());
        }
        user.setId(user.getId());
        users.put(user.getId(), user);
        log.info("Пользователь обновлен - , {}", user);
        return user;
    }
}
