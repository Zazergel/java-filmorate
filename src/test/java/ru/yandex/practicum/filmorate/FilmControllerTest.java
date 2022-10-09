package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FilmControllerTest {
    Film film = new Film();
    FilmController filmController = new FilmController();

    @Test
    void getFilmAlreadyExistException() throws ValidationException {
        film.setDuration(120);
        film.setDescription("Описание тестового фильма 1");
        film.setName("Тестовый фильм 1");
        film.setReleaseDate(LocalDate.of(2012, 12, 12));
        filmController.addNewFilm(film);
        assertThrows(ValidationException.class, () -> filmController.addNewFilm(film));
    }

    @Test
    void getInvalidFilmNameException() {
        film.setDuration(120);
        film.setDescription("Описание тестового фильма 1");
        film.setName(" ");
        film.setReleaseDate(LocalDate.of(2012, 12, 12));
        assertThrows(ValidationException.class, () -> filmController.addNewFilm(film));
    }
    @Test
    void getInvalidDescriptionException() {
        film.setDuration(120);
        film.setDescription("Тестовое описание на многабукафТестовое описание на многабукаф" +
                "Тестовое описание на многабукафТестовое описание на многабукаф" +
                "Тестовое описание на многабукафТестовое описание на многабукафТестовое описание на многабукаф" +
                "Тестовое описание на многабукафТестовое описание на многабукаф" +
                "Тестовое описание на многабукафТестовое описание на многабукаф" +
                "Тестовое описание на многабукафТестовое описание на многабукаф");
        film.setName("Тестовый фильм 1");
        film.setReleaseDate(LocalDate.of(2011, 10, 14));
        assertThrows(ValidationException.class, () -> filmController.addNewFilm(film));
    }
    @Test
    void getInvalidReleaseDateException() {
        film.setDuration(120);
        film.setDescription("Описание тестового фильма 1");
        film.setName("Тестовый фильм 1");
        film.setReleaseDate(LocalDate.of(1212, 12, 12));
        assertThrows(ValidationException.class, () -> filmController.addNewFilm(film));
    }
    @Test
    void getInvalidDurationException() {
        film.setDuration(-120);
        film.setDescription("Описание тестового фильма 1");
        film.setName("Тестовый фильм 1");
        film.setReleaseDate(LocalDate.of(1212, 12, 12));
        assertThrows(ValidationException.class, () -> filmController.addNewFilm(film));
    }
    @Test
    void getInvalidFilmException() {
        assertThrows(ValidationException.class, () -> filmController.updateFilm(film));
    }
}