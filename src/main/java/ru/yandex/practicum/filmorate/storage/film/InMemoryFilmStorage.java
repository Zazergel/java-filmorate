package ru.yandex.practicum.filmorate.storage.film;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.services.validationServices.FilmValidationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private final FilmValidationService filmValidateService;
    private final Map<Integer, Film> films = new HashMap<>();
    private int idGen = 1;

    @Autowired
    public InMemoryFilmStorage(FilmValidationService filmValidateService) {
        this.filmValidateService = filmValidateService;
    }

    //Получаем один фильм, если он есть
    @Override
    public Film getFilm(Integer id) {
        filmValidateService.checkGetFilmValidation(log, films, id);
        return films.get(id);
    }

    //Получаем список всех фильмов по запросу
    @Override
    public List<Film> getAllFilms() {
        log.debug("Количество фильмов всего: {}", films.size());
        return new ArrayList<>(films.values());
    }

    //Добавляем новый фильм в коллекцию, если он проходит по параметрам
    @Override
    public Film addNewFilm(Film film) {
        filmValidateService.checkPostFilmValidation(log, films, film);
        film.setId(idGen++);
        films.put(film.getId(), film);
        log.info("Добавлен новый фильм, {}", film);
        return film;
    }

    //Обновляем фильм в коллекции, проверив его наличие
    @Override
    public Film updateFilm(Film film) {
        filmValidateService.checkGetFilmValidation(log, films, film.getId());
        films.put(film.getId(), film);
        log.info("Фильм обновлен - , {}", film);
        return film;
    }


}
