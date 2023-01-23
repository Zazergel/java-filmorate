package ru.yandex.practicum.filmorate.services.film;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.interfaces.UserStorage;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);


    //Получаем коллекцию самых популярных фильмов
    public List<Film> getMostPopularFilms(Integer count) {
        return filmStorage.getAllFilms().stream()
                .sorted(this::compare)
                .limit(count)
                .collect(Collectors.toList());
    }

    //Добавляем лайк фильму от конкретного пользователя, проверив, существуют ли они
    public Film addLike(Integer id, Integer userId) {
        if (!filmStorage.getAllFilms().contains(filmStorage.getFilm(id))) {
            log.error("Такого фильма не существует!, {}", id);
            throw new NotFoundException("Такого фильма не существует!");
        }
        if (!userStorage.getAllUsers().contains(userStorage.getUser(userId))) {
            log.error("Такого пользователя не существует!, {}", userId);
            throw new NotFoundException("Такого пользователя не существует!");
        }
        filmStorage.getFilm(id).getLikes().add(userId);
        log.info("Пользователь с id:" + userId + " поставил лайк фильму с id:" + id);
        return filmStorage.getFilm(id);
    }

    //Удаляем лайк фильму от конкретного пользователя, проверив, существуют ли они, и не был ли удален этот лайк ранее
    public Film removeLike(Integer id, Integer userId) {
        if (!filmStorage.getAllFilms().contains(filmStorage.getFilm(id))) {
            log.error("Такого фильма не существует!, {}", id);
            throw new NotFoundException("Такого фильма не существует!");
        }
        if (!userStorage.getAllUsers().contains(userStorage.getUser(userId))) {
            log.error("Такого пользователя не существует!, {}", userId);
            throw new NotFoundException("Такого пользователя не существует!");
        }
        if (!filmStorage.getFilm(id).getLikes().contains(userId)) {
            log.error("У фильма с id" + id + " Нет лайка от пользователя с id:" + userId);
            throw new NotFoundException("У фильма нет лайка от этого пользователя");
        }
        filmStorage.getFilm(id).getLikes().remove(userId);
        log.info("Пользователь с id:" + userId + " удалил свой лайк фильму с id:" + id);
        return filmStorage.getFilm(id);
    }

    public int compare(Film f0, Film f1) {
        return Integer.compare(f1.getLikes().size(), f0.getLikes().size());
    }

}
