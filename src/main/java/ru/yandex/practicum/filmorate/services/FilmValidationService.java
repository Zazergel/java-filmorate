package ru.yandex.practicum.filmorate.services;

import org.slf4j.Logger;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Map;

public class FilmValidationService {

    public void checkPutFilmValidation(Logger log, Map<Integer, Film> films, Film film) {
        if (!films.containsKey(film.getId())) {
            log.error("Такого фильма не существует!, {}", film);
            throw new ValidationException("Такого фильма не существует!");
        }
    }

    public void checkPostFilmValidation(Logger log, Map<Integer, Film> films, Film film) {
        if (films.containsKey(film.getId())) {
            log.error("Такой фильм уже есть!, {}", film);
            throw new ValidationException("Такой фильм уже есть!");
        } else if (film.getName().isBlank()) {
            log.error("Название фильма не может быть пустым!, {}", film);
            throw new ValidationException("Название фильма не может быть пустым!");
        } else if (film.getDescription().length() > 200) {
            log.error("Максимальная длина описания — 200 символов!, {}", film);
            throw new ValidationException("Максимальная длина описания — 200 символов!");
        } else if (!film.getReleaseDate().isAfter(LocalDate.of(1895, 12, 28))) {
            log.error("Дата релиза не должна быть раньше 28 декабря 1895!, {}", film);
            throw new ValidationException("Дата релиза не должна быть раньше 28 декабря 1895!");
        } else if (film.getDuration() <= 0) {
            log.error("Продолжительность фильма должна быть положительна!, {}", film);
            throw new ValidationException("Продолжительность фильма должна быть положительна!");
        }
    }
}
