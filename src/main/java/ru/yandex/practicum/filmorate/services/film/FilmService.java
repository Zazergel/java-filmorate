package ru.yandex.practicum.filmorate.services.film;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.interfaces.UserStorage;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);


    //Добавляем новый фильм по запросу, проверив, проходит ли он по параметрам
    public Film addFilm(Film film) {
        checkPostFilmValidation(log, film);
        filmStorage.addNewFilm(film);
        return film;
    }

    //Получаем коллекцию самых популярных фильмов
    public List<Film> getMostPopularFilms(Integer count) {
        return filmStorage.getAllFilms().stream()
                .sorted(this::compare)
                .limit(count)
                .collect(Collectors.toList());
    }

    //Добавляем лайк фильму от конкретного пользователя, проверив, существуют ли они
    public Film addLike(Integer id, Integer userId) {
        filmStorage.getFilm(id);
        userStorage.getUser(userId);
        filmStorage.getFilm(id).getLikes().add(userId);
        log.info("Пользователь с id:" + userId + " поставил лайк фильму с id:" + id);
        return filmStorage.getFilm(id);
    }

    //Удаляем лайк фильму от конкретного пользователя, проверив, существуют ли они, и не был ли удален этот лайк ранее
    public Film removeLike(Integer id, Integer userId) {
        filmStorage.getFilm(id);
        userStorage.getUser(userId);
        if (!filmStorage.getFilm(id).getLikes().contains(userId)) {
            log.error("У фильма с id" + id + " Нет лайка от пользователя с id:" + userId);
            throw new NotFoundException("У фильма нет лайка от этого пользователя");
        }
        filmStorage.getFilm(id).getLikes().remove(userId);
        log.info("Пользователь с id:" + userId + " удалил свой лайк фильму с id:" + id);
        return filmStorage.getFilm(id);
    }

    //Проверка добавления нового фильма в соответствии с требованиями
    public void checkPostFilmValidation(Logger log, Film film) {
        if (filmStorage.getAllFilms().contains(film)) {
            log.error("Такой фильм уже есть!, {}", film);
            throw new ValidationException("Такой фильм уже есть!");
        } else if (!film.getReleaseDate().isAfter(LocalDate.of(1895, 12, 28))) {
            log.error("Дата релиза не должна быть раньше 28 декабря 1895!, {}", film);
            throw new ValidationException("Дата релиза не должна быть раньше 28 декабря 1895!");
        }
    }

    public int compare(Film f0, Film f1) {
        return Integer.compare(f1.getLikes().size(), f0.getLikes().size());
    }

}
