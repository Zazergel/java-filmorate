package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.services.filmService.FilmService;

import javax.validation.Valid;
import java.util.List;

@Component
@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmStorage filmStorage;
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmStorage filmStorage, FilmService filmService) {
        this.filmStorage = filmStorage;
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable("id") Integer id) {
        if (id <= 0) {
            throw new NotFoundException("id");
        }
        return filmStorage.getFilm(id);
    }

    @GetMapping("/popular")
    public List<Film> getMostPopularFilms(@RequestParam(defaultValue = "10", required = false) Integer count) {
        if (count < 0) {
            throw new NotFoundException("count");
        }
        return filmService.getMostPopularFilms(count);
    }

    @PostMapping
    public Film addNewFilm(@RequestBody @Valid Film film) {
        return filmStorage.addNewFilm(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid Film film) {
        return filmStorage.updateFilm(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable("id") Integer id,
                        @PathVariable("userId") Integer userId) {
        if (id <= 0) {
            throw new NotFoundException("id");
        }
        if (userId <= 0) {
            throw new NotFoundException("userId");
        }
        return filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film removeLike(@PathVariable("id") Integer id,
                           @PathVariable("userId") Integer userId) {
        if (id <= 0) {
            throw new NotFoundException("id");
        }
        if (userId <= 0) {
            throw new NotFoundException("userId");
        }
        return filmService.removeLike(id, userId);
    }
}
