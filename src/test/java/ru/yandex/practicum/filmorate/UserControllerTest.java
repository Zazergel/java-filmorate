package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserControllerTest {

    User user = new User();
    UserController userController = new UserController();

    @Test
    void getUserAlreadyExistException() throws ValidationException {
        user.setBirthday(LocalDate.parse("1997-06-28"));
        user.setLogin("IGOR");
        user.setEmail("igorGod@yandex.ru");
        userController.addNewUser(user);
        assertThrows(ValidationException.class, () -> userController.addNewUser(user));
    }

    @Test
    void getInvalidEmailException() {
        user.setBirthday(LocalDate.parse("1997-06-28"));
        user.setLogin("IGOR");
        user.setEmail("");
        assertThrows(ValidationException.class, () -> userController.addNewUser(user));
    }

    @Test
    void getInvalidLoginException() {
        user.setBirthday(LocalDate.parse("1997-06-28"));
        user.setLogin("  ");
        user.setEmail("igorGod@yandex.ru");
        assertThrows(ValidationException.class, () -> userController.addNewUser(user));
    }

    @Test
    void getInvalidBirthdayException() {
        user.setBirthday(LocalDate.parse("2200-01-12"));
        user.setLogin("Igor");
        user.setEmail("igorGod@yandex.ru");
        assertThrows(ValidationException.class, () -> userController.addNewUser(user));
    }

    @Test
    void getInvalidUserException() {
        assertThrows(ValidationException.class, () -> userController.updateNewUser(user));
    }
}