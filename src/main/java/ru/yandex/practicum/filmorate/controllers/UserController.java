package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.services.user.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public Collection<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") @Positive Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/{id}/friends")
    public List<User> getAllFriends(@PathVariable("id") @Positive long id) {
        return userService.getAllFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getMutualFriends(@PathVariable("id") @Positive long userId,
                                       @PathVariable("otherId") @Positive long otherId) {
        return userService.getMutualFriends(userId, otherId);
    }

    @PostMapping
    public User addNewUser(@RequestBody @Valid User user) {
        return userService.addUser(user);
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User user) {
        return userService.updateUser(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable("id") @Positive long userId,
                          @PathVariable("friendId") @Positive long friendId) {
        userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable("id") @Positive long userId,
                             @PathVariable("friendId") @Positive long friendId) {
        userService.removeFriend(userId, friendId);
    }
}
