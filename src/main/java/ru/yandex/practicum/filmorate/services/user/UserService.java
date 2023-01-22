package ru.yandex.practicum.filmorate.services.user;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.interfaces.UserStorage;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.services.validationServices.UserValidationService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserStorage userStorage;
    private final UserValidationService userValidationService;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    //Получаем список всех друзей конкретного пользователя по запросу, проверив, существует ли он
    public List<User> getAllFriends(Integer id) {
        if (userStorage.getUser(id) == null) {
            log.error("Такого пользователя не существует!, {}", id);
            throw new NotFoundException("Такого пользователя не существует!");
        }
        log.debug("Количество друзей пользователя c id:" + id + " = " + (int) userStorage.getUser(id).getFriends().stream()
                .map(userStorage::getUser).count());
        return userStorage.getUser(id).getFriends().stream()
                .map(userStorage::getUser)
                .collect(Collectors.toList());
    }

    //Получаем список общих друзей у двух пользователей по запросу, проверив, существуют ли они
    public List<User> getCommonFriends(Integer id, Integer otherId) {
        if (userStorage.getUser(id) == null) {
            log.error("Такого пользователя не существует!, {}", id);
            throw new NotFoundException("Такого пользователя не существует!");
        }
        if (userStorage.getUser(otherId) == null) {
            log.error("Такого пользователя не существует!, {}", otherId);
            throw new NotFoundException("Такого пользователя не существует!");
        }
        List<User> commonFriends = new ArrayList<>();
        List<Integer> friendsByUser = new ArrayList<>(userStorage.getUser(id).getFriends());
        for (int i = 0; i < userStorage.getUser(id).getFriends().size(); i++) {
            if (userStorage.getUser(otherId).getFriends().contains(friendsByUser.get(i))) {
                commonFriends.add(userStorage.getUser(friendsByUser.get(i)));
            }
        }
        log.debug("Число общих друзей у пользователей с id:" + id + " и id:" + otherId + " = " + commonFriends.size());
        return commonFriends;
    }

    //Добавляем двух пользователей друг к другу в друзья, проверив, существуют ли они
    public User addFriend(Integer id, Integer friendId) {
        if (userStorage.getUser(id) == null) {
            log.error("Такого пользователя не существует!, {}", id);
            throw new NotFoundException("Такого пользователя не существует!");
        }
        if (userStorage.getUser(friendId) == null) {
            log.error("Такого пользователя не существует!, {}", friendId);
            throw new NotFoundException("Такого пользователя не существует!");
        }
        userStorage.getUser(id).getFriends().add(friendId);
        userStorage.getUser(friendId).getFriends().add(id);
        log.info("Пользователи с id:" + id + " и id:" + friendId + " - подружились!");
        return userStorage.getUser(id);
    }

    //Удаляем пользователей из списка друзей друг друга, проверив, существуют ли они, и не было ли это сделано ранее
    public User removeFriend(Integer id, Integer friendId) {
        if (userStorage.getUser(id) == null) {
            log.error("Такого пользователя не существует!, {}", id);
            throw new NotFoundException("Такого пользователя не существует!");
        }
        if (userStorage.getUser(friendId) == null) {
            log.error("Такого пользователя не существует!, {}", friendId);
            throw new NotFoundException("Такого пользователя не существует!");
        }
        userValidationService.checkRemoveFriendValidate(log, userStorage, id, friendId);
        userStorage.getUser(id).getFriends().remove(friendId);
        userStorage.getUser(friendId).getFriends().remove(id);
        log.info("Пользователи с id:" + id + " и id:" + friendId + " - прекратили дружбу!");
        return userStorage.getUser(id);
    }

}
