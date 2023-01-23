package ru.yandex.practicum.filmorate.services.validationServices;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Map;

@Service
public class FilmValidationService {

    //Проверка добавления нового фильма в соответствии с требованиями
    public void checkPostFilmValidation(Logger log, Map<Integer, Film> films, Film film) {
        if (films.containsKey(film.getId())) {
            log.error("Такой фильм уже есть!, {}", film);
            throw new ValidationException("Такой фильм уже есть!");
        } else if (!film.getReleaseDate().isAfter(LocalDate.of(1895, 12, 28))) {
            log.error("Дата релиза не должна быть раньше 28 декабря 1895!, {}", film);
            throw new ValidationException("Дата релиза не должна быть раньше 28 декабря 1895!");
        }
    }

    //На дубликаты лайков мы не проверяем, тк коллекция СЕТ сама по себе не допускает дубликатов
}
