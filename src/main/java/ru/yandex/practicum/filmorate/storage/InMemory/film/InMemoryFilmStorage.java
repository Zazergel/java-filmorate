package ru.yandex.practicum.filmorate.storage.InMemory.film;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.interfaces.FilmStorageIm;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class InMemoryFilmStorage implements FilmStorageIm {
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private final Map<Long, Film> films = new HashMap<>();
    private long idGen = 1;

    @Autowired
    public InMemoryFilmStorage() {
    }

    //Получаем один фильм, если он есть
    @Override
    public Film getFilm(Long filmId) {
        if (!films.containsKey(filmId)) {
            log.error("Такого фильма не существует!, {}", filmId);
            throw new NotFoundException("Такого фильма не существует!");
        }
        return films.get(filmId);
    }

    //Получаем список всех фильмов по запросу
    @Override
    public List<Film> getAllFilms() {
        log.debug("Количество фильмов всего: {}", films.size());
        return new ArrayList<>(films.values());
    }

    //Добавляем новый фильм в коллекцию
    @Override
    public Film addNewFilm(Film film) {
        film.setId(idGen++);
        films.put(film.getId(), film);
        log.info("Добавлен новый фильм, {}", film);
        return film;
    }

    //Обновляем фильм в коллекции, проверив его наличие
    @Override
    public Film updateFilm(Film film) {
        getFilm(film.getId());
        films.put(film.getId(), film);
        log.info("Фильм обновлен - , {}", film);
        return film;
    }


}
