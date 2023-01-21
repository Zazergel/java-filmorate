package ru.yandex.practicum.filmorate.interfaces;

import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.Map;

public interface UserStorage {

    ArrayList<User> getAllUsers();

    Map<Integer, User> getUsers();

    User addNewUser(User user);

    User updateNewUser(User user);

    User getUser(Integer id);
}
