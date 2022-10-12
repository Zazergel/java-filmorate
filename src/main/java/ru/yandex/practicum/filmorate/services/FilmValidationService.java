package ru.yandex.practicum.filmorate.services;

import org.slf4j.Logger;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Map;
 //Оставил только кастомные проверки
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
        } else if (!film.getReleaseDate().isAfter(LocalDate.of(1895, 12, 28))) {
            log.error("Дата релиза не должна быть раньше 28 декабря 1895!, {}", film);
            throw new ValidationException("Дата релиза не должна быть раньше 28 декабря 1895!");
        }
    }
}
