package ru.yandex.practicum.filmorate.services.userService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.interfaces.UserStorage;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.services.validationServices.UserValidationService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserStorage userStorage;
    private final UserValidationService userValidationService;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserService(UserStorage userStorage, UserValidationService userValidationService) {
        this.userStorage = userStorage;
        this.userValidationService = userValidationService;
    }

    //Получаем список всех друзей конкретного пользователя по запросу, проверив, существует ли он
    public List<User> getAllFriends(Integer id) {
        userValidationService.checkGetUserValidate(log, userStorage.getUsers(), id);
        return userStorage.getUsers().values().stream()
                .filter(p -> userStorage.getUsers().get(id).getFriends().contains(p.getId()))
                .collect(Collectors.toList());
    }

    //Получаем список общих друзей у двух пользователей по запросу, проверив, существуют ли они
    public List<User> getCommonFriends(Integer id, Integer otherId) {
        userValidationService.checkGetUserValidate(log, userStorage.getUsers(), id);
        userValidationService.checkGetUserValidate(log, userStorage.getUsers(), otherId);
        ArrayList<User> commonFriends = new ArrayList<>();
        ArrayList<Integer> friendsByUser = new ArrayList<>(userStorage.getUsers().get(id).getFriends());
        for (int i = 0; i < userStorage.getUsers().get(id).getFriends().size(); i++) {
            if (userStorage.getUsers().get(otherId).getFriends().contains(friendsByUser.get(i))) {
                commonFriends.add(userStorage.getUsers().get(friendsByUser.get(i)));
            }
        }
        return commonFriends;
    }

    //Добавляем двух пользователей друг к другу в друзья, проверив, существуют ли они
    public User addFriend(Integer id, Integer friendId) {
        userValidationService.checkGetUserValidate(log, userStorage.getUsers(), id);
        userValidationService.checkGetUserValidate(log, userStorage.getUsers(), friendId);
        userStorage.getUsers().get(id).getFriends().add(friendId);
        userStorage.getUsers().get(friendId).getFriends().add(id);
        return userStorage.getUsers().get(id);
    }

    //Удаляем пользователей из списка друзей друг друга, проверив, существуют ли они, и не было ли это сделано ранее
    public User removeFriend(Integer id, Integer friendId) {
        userValidationService.checkGetUserValidate(log, userStorage.getUsers(), id);
        userValidationService.checkGetUserValidate(log, userStorage.getUsers(), friendId);
        userValidationService.checkRemoveFriendValidate(userStorage, id, friendId);
        userStorage.getUsers().get(id).getFriends().remove(friendId);
        userStorage.getUsers().get(friendId).getFriends().remove(id);
        return userStorage.getUsers().get(id);
    }

}
