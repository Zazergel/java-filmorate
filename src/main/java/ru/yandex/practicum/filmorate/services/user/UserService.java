package ru.yandex.practicum.filmorate.services.user;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.interfaces.dal.FriendShipStorage;
import ru.yandex.practicum.filmorate.interfaces.dal.UserStorage;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserStorage userStorage;
    private final FriendShipStorage friendShipStorage;
    private long idGen = 1;

    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User getUser(long userId) {
        return userStorage.getUser(userId);
    }

    //Получаем список всех друзей конкретного пользователя по запросу
    public List<User> getAllFriends(long userId) {
        userStorage.getUser(userId);
        List<User> friendsList = friendShipStorage.getListOfFriends(userId).stream()
                .map(userStorage::getUser)
                .collect(Collectors.toList());
        log.debug("Количество друзей пользователя c id:" + userId + " = " + friendShipStorage
                .getListOfFriends(userId).stream()
                .map(userStorage::getUser).count());
        return friendsList;
    }

    //Получаем список общих друзей у двух пользователей по запросу
    public List<User> getMutualFriends(long id, long otherId) {
        userStorage.getUser(id);
        userStorage.getUser(otherId);
        List<User> mutualFriends = friendShipStorage.getAListOfMutualFriends(id, otherId).stream()
                .map(userStorage::getUser)
                .collect(Collectors.toList());
        log.debug("Число общих друзей у пользователей с id:" + id + " и id:" + otherId + " = " + mutualFriends.size());
        return mutualFriends;
    }

    //Добавляем двух пользователей друг к другу в друзья
    public void addFriend(long userId, long friendId) {
        userStorage.getUser(userId);
        userStorage.getUser(friendId);
        friendShipStorage.addAsFriend(userId, friendId);
        log.info("Пользователи с id:" + userId + " и id:" + friendId + " - подружились!");
    }

    //Удаляем пользователей из списка друзей друг друга
    public void removeFriend(long userId, long friendId) {
        userStorage.getUser(userId);
        userStorage.getUser(friendId);
        checkRemoveFriendValidate(userId, friendId);
        friendShipStorage.removeFromFriends(userId, friendId);
        log.info("Пользователи с id:" + userId + " и id:" + friendId + " - прекратили дружбу!");

    }

    //Добавляем нового пользователя по запросу, если он проходит валидацию
    public User addUser(User user) {
        checkUserNameForBlankOrNull(user);
        checkPostUserValidate(user);
        user.setId(idGen);
        userStorage.addUser(user);
        idGen++;
        log.info("Добавлен пользователь: " + user);
        return user;
    }

    //Обновляем пользователя по запросу, если он вообще есть, проверяя его имя на пустоту
    public User updateUser(User user) {
        User beforeUser = userStorage.getUser(user.getId());
        checkUserNameForBlankOrNull(user);
        userStorage.updateUser(user);
        log.info("Данные пользователя: " + beforeUser + " Обновлены на: " + user);
        return user;
    }

    //Проверка добавления нового пользователя в соответствии с требованиями
    private void checkPostUserValidate(User user) {
        if (userStorage.getAllUsers().contains(user)) {
            log.error("Такой пользователь уже существует!, {}", user);
            throw new ValidationException("Такой пользователь уже существует!");
        } else if (user.getLogin().equals("") || user.getLogin().contains(" ")) {
            log.error("Логин не может быть пустым или содержать пробелы!, {}", user);
            throw new ValidationException("Логин не может быть пустым или содержать пробелы!");
        }
    }

    //Проверка имени пользователя на пустоту, если это так, то заполняем поле имени - логином.
    private void checkUserNameForBlankOrNull(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.info("Имя пользователя изменено на его логин, т.к. оно было пустым.");
        }
    }

    //Проверяем удален ли пользователь из друзей
    private void checkRemoveFriendValidate(long userId, long friendId) {
        if (!userStorage.getUser(userId).getFriends().contains(friendId)) {
            log.error("Пользователи с id:" + userId + " и id:" + friendId + " - не друзья");
            throw new ValidationException("Этого пользователя уже нет в друзьях");
        }
    }


}
