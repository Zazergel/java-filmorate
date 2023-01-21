package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.services.userService.UserService;
import ru.yandex.practicum.filmorate.interfaces.UserStorage;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Component
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserStorage userStorage;
    private final UserService userService;

    @Autowired
    public UserController(UserStorage userStorage, UserService userService) {
        this.userStorage = userStorage;
        this.userService = userService;
    }

    @GetMapping
    public ArrayList<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Integer id) {
        if (id <= 0) {
            throw new NotFoundException("id");
        }
        return userStorage.getUser(id);
    }

    @GetMapping("/{id}/friends")
    public List<User> getAllFriends(@PathVariable("id") Integer id) {
        if (id <= 0) {
            throw new NotFoundException("id");
        }
        return userService.getAllFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable("id") Integer id,
                                       @PathVariable("otherId") Integer otherId) {
        if (id <= 0) {
            throw new NotFoundException("id");
        }
        if (otherId <= 0) {
            throw new NotFoundException("otherId");
        }
        return userService.getCommonFriends(id, otherId);
    }


    @PostMapping
    public User addNewUser(@RequestBody @Valid User user) {
        return userStorage.addNewUser(user);
    }

    @PutMapping
    public User updateNewUser(@RequestBody @Valid User user) {
        return userStorage.updateNewUser(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable("id") Integer id,
                          @PathVariable("friendId") Integer friendId) {
        if (id <= 0) {
            throw new NotFoundException("id");
        }
        if (friendId <= 0) {
            throw new NotFoundException("friendId");
        }
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User removeFriend(@PathVariable("id") Integer id,
                             @PathVariable("friendId") Integer friendId) {
        if (id <= 0) {
            throw new NotFoundException("id");
        }
        if (friendId <= 0) {
            throw new NotFoundException("friendId");
        }
        return userService.removeFriend(id, friendId);
    }
}
