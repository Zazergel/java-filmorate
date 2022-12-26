package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.services.FilmValidationService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/films")
public class FilmController {
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private final FilmValidationService filmValidService = new FilmValidationService();
    private final Map<Integer, Film> films = new HashMap<>();
    private int idGen = 1;

    @GetMapping
    public List<Film> getFilms() {
        log.debug("Количество фильмов всего: {}", films.size());
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film addNewFilm(@Valid @RequestBody Film film) {
        filmValidService.checkPostFilmValidation(log, films, film);
        film.setId(idGen++);
        films.put(film.getId(), film);
        log.info("Добавлен новый фильм, {}", film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        filmValidService.checkPutFilmValidation(log, films, film);
        film.setId(film.getId());
        films.put(film.getId(), film);
        log.info("Фильм обновлен - , {}", film);
        return film;
    }
}
