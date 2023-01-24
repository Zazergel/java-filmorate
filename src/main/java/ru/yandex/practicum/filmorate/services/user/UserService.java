package ru.yandex.practicum.filmorate.services.user;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.interfaces.UserStorage;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserStorage userStorage;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    //Получаем список всех друзей конкретного пользователя по запросу
    public List<User> getAllFriends(Integer id) {
        User currentUser = userStorage.getUser(id);
        log.debug("Количество друзей пользователя c id:" + id + " = " + (int) currentUser.getFriends().stream()
                .map(userStorage::getUser).count());
        return currentUser.getFriends().stream()
                .map(userStorage::getUser)
                .collect(Collectors.toList());
    }

    //Получаем список общих друзей у двух пользователей по запросу
    public List<User> getCommonFriends(Integer id, Integer otherId) {
        User currentUser = userStorage.getUser(id);
        User otherUser = userStorage.getUser(otherId);
        List<User> commonFriends = new ArrayList<>();
        List<Integer> friendsByUser = new ArrayList<>(currentUser.getFriends());
        for (int i = 0; i < currentUser.getFriends().size(); i++) {
            if (otherUser.getFriends().contains(friendsByUser.get(i))) {
                commonFriends.add(userStorage.getUser(friendsByUser.get(i)));
            }
        }
        log.debug("Число общих друзей у пользователей с id:" + id + " и id:" + otherId + " = " + commonFriends.size());
        return commonFriends;
    }

    //Добавляем двух пользователей друг к другу в друзья
    public User addFriend(Integer id, Integer friendId) {
        User currentUser = userStorage.getUser(id);
        User friendUser = userStorage.getUser(friendId);
        currentUser.getFriends().add(friendId);
        friendUser.getFriends().add(id);
        log.info("Пользователи с id:" + id + " и id:" + friendId + " - подружились!");
        return currentUser;
    }

    //Удаляем пользователей из списка друзей друг друга
    public User removeFriend(Integer id, Integer friendId) {
        User currentUser = userStorage.getUser(id);
        User friendUser = userStorage.getUser(friendId);
        checkRemoveFriendValidate(id, friendId);
        currentUser.getFriends().remove(friendId);
        friendUser.getFriends().remove(id);
        log.info("Пользователи с id:" + id + " и id:" + friendId + " - прекратили дружбу!");
        return currentUser;
    }

    //Добавляем нового пользователя по запросу, если он проходит валидацию
    public User addUser(User user) {
        checkUserNameForBlankOrNull(user);
        checkPostUserValidate(user);
        userStorage.addNewUser(user);
        return user;
    }

    //Обновляем пользователя по запросу, если он вообще есть, проверяя его имя на пустоту
    public User updateUser(User user) {
        userStorage.getUser(user.getId());
        checkUserNameForBlankOrNull(user);
        userStorage.updateNewUser(user);
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
    private void checkRemoveFriendValidate(Integer id, Integer friendId) {
        if (!userStorage.getUser(id).getFriends().contains(friendId)) {
            log.error("Пользователи с id:" + id + " и id:" + friendId + " - не друзья");
            throw new ValidationException("Этого пользователя уже нет в друзьях");
        }
    }


}
