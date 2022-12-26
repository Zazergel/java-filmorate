package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {
    User user = new User();
    UserController userController = new UserController();

    private ValidatorFactory validatorFactory;
    private Validator validator;

    @BeforeEach
    void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterEach
    void close() {
        validatorFactory.close();
    }

    @Test
    void checkUserForAlreadyExistException() throws ValidationException {
        user.setBirthday(LocalDate.parse("1997-06-28"));
        user.setLogin("IGOR");
        user.setEmail("igorGod@yandex.ru");
        userController.addNewUser(user);
        assertThrows(ValidationException.class, () -> userController.addNewUser(user));
    }

    @Test
    void checkUserForInvalidEmail() {
        user.setBirthday(LocalDate.parse("1997-06-28"));
        user.setLogin("IGOR");
        user.setEmail("@fhgj@jngjf");
        Set<ConstraintViolation<User>> violationSet = validator.validate(user);
        for (ConstraintViolation<User> violation : violationSet) {
            System.out.println("Пользователь не был добавлен так как: " + violation.getMessage());
        }
        assertFalse(violationSet.isEmpty());
    }

    @Test
    void checkUserForInvalidEmptyLogin() {
        user.setBirthday(LocalDate.parse("1997-06-28"));
        user.setLogin("");
        user.setEmail("igorGod@yandex.ru");
        Set<ConstraintViolation<User>> violationSet = validator.validate(user);
        for (ConstraintViolation<User> violation : violationSet) {
            System.out.println("Пользователь не был добавлен так как: " + violation.getMessage());
        }
        assertFalse(violationSet.isEmpty());
    }

    @Test
    void checkUserForInvalidLoginWithSpaces() {
        user.setBirthday(LocalDate.parse("1997-06-28"));
        user.setLogin("Marty Mac Fly");
        user.setEmail("igorGod@yandex.ru");
        Set<ConstraintViolation<User>> violationSet = validator.validate(user);
        for (ConstraintViolation<User> violation : violationSet) {
            System.out.println("Пользователь не был добавлен так как: " + violation.getMessage());
        }
        assertThrows(ValidationException.class, () -> userController.addNewUser(user));
        assertTrue(violationSet.isEmpty());

    }

    @Test
    void checkUserForInvalidBirthday() {
        user.setBirthday(LocalDate.parse("2200-01-12"));
        user.setLogin("Igor");
        user.setEmail("igorGod@yandex.ru");
        Set<ConstraintViolation<User>> violationSet = validator.validate(user);
        for (ConstraintViolation<User> violation : violationSet) {
            System.out.println("Пользователь не был добавлен так как: " + violation.getMessage());
        }
        assertFalse(violationSet.isEmpty());
    }

    @Test
    void checkUserForInvalidUpdateUser() {
        assertThrows(ValidationException.class, () -> userController.updateNewUser(user));
    }
}