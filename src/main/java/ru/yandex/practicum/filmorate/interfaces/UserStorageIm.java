package ru.yandex.practicum.filmorate.interfaces;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorageIm {

    List<User> getAllUsers();

    User addNewUser(User user);

    User updateNewUser(User user);

    User getUser(Long id);
}
