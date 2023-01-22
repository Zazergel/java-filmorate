package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.interfaces.UserStorage;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.services.user.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;


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
    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") @Positive Integer id) {
        return userStorage.getUser(id);
    }

    @GetMapping("/{id}/friends")
    public List<User> getAllFriends(@PathVariable("id") @Positive Integer id) {
        return userService.getAllFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable("id") @Positive Integer id,
                                       @PathVariable("otherId") @Positive Integer otherId) {
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
    public User addFriend(@PathVariable("id") @Positive Integer id,
                          @PathVariable("friendId") @Positive Integer friendId) {
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User removeFriend(@PathVariable("id") @Positive Integer id,
                             @PathVariable("friendId") @Positive Integer friendId) {
        return userService.removeFriend(id, friendId);
    }
}
