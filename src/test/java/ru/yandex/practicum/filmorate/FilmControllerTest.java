package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class FilmControllerTest {
    Film film = new Film();
    FilmController filmController = new FilmController();

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
    void checkFilmForInvalidName() {
        film.setDuration(120);
        film.setDescription("Описание тестового фильма 1");
        film.setName(" "); //Устанавливаем заведомо неправильное имя
        film.setReleaseDate(LocalDate.of(2012, 12, 12));
        Set<ConstraintViolation<Film>> violationSet = validator.validate(film);
        //Если сет с сообщениями об ошибках что-то в себе содержит, то вызовем у каждого элемента его описание
        for (ConstraintViolation<Film> violation : violationSet) {
            System.out.println("Фильм не был добавлен так как: " + violation.getMessage());
        }
        assertFalse(violationSet.isEmpty()); //Если есть какие-то ошибки, то тест пройден!
    }

    @Test
    void checkFilmForInvalidDescription() {
        film.setDuration(120);
        //Устанавливаем заведомо неправильное описание фильма
        film.setDescription("Описание тестового фильма с воооооооооооооооооооооооооооооооооооооооооооооооооооо" +
                "ооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооо" +
                "оооооооооооооооооооооооооооооооооооооооооооооооооооо" +
                "ооооооооооооооооооооооооооооооооооооооооооооооооооот таким описанием!");
        film.setName("Тестовый фильм");
        film.setReleaseDate(LocalDate.of(2012, 12, 12));
        Set<ConstraintViolation<Film>> violationSet = validator.validate(film);
        for (ConstraintViolation<Film> violation : violationSet) {
            System.out.println("Фильм не был добавлен так как: " + violation.getMessage());
        }
        assertFalse(violationSet.isEmpty());
    }

    @Test
    void checkFilmForInvalidReleaseDate() {
        film.setDuration(120);
        film.setDescription("Описание тестового фильма 1");
        film.setName("Тестовый фильм 1");
        //Устанавливаем заведомо неправильную дату релиза
        film.setReleaseDate(LocalDate.of(1212, 12, 12));
        //Кидаем исключение если что-то не так
        assertThrows(ValidationException.class, () -> filmController.addNewFilm(film));
    }

    @Test
    void checkFilmForAlreadyExistException() throws ValidationException {
        film.setDuration(120);
        film.setDescription("Описание тестового фильма 1");
        film.setName("Тестовый фильм 1");
        film.setReleaseDate(LocalDate.of(2012, 12, 12));
        filmController.addNewFilm(film); //добавляем фильм
        //Добавляем фильм еще раз, и если он там уже есть, то кидаем исключение
        assertThrows(ValidationException.class, () -> filmController.addNewFilm(film));
    }

    @Test
    void checkFilForInvalidFilmUpdate() {
        //Пытаемся обновить то, чего нет
        assertThrows(ValidationException.class, () -> filmController.updateFilm(film));
    }
}