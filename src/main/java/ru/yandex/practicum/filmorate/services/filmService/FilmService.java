package ru.yandex.practicum.filmorate.services.filmService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.interfaces.UserStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.services.validationServices.FilmValidationService;
import ru.yandex.practicum.filmorate.services.validationServices.UserValidationService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService implements Comparator<Film> {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final FilmValidationService filmValidateService;
    private final UserValidationService userValidateService;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage,
                       FilmValidationService filmValidateService, UserValidationService userValidateService) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.filmValidateService = filmValidateService;
        this.userValidateService = userValidateService;
    }

    //Получаем коллекцию самых популярных фильмов
    public List<Film> getMostPopularFilms(Integer count) {
        return filmStorage.getFilms().values().stream()
                .sorted(this)
                .limit(count)
                .collect(Collectors.toList());
    }

    //Добавляем лайк фильму от конкретного пользователя, проверив, существуют ли они
    public Film addLike(Integer id, Integer userId) {
        filmValidateService.checkGetFilmValidation(log, filmStorage.getFilms(), id);
        userValidateService.checkGetUserValidate(log, userStorage.getUsers(), id);
        filmStorage.getFilms().get(id).getLikes().add(userId);
        return filmStorage.getFilms().get(id);
    }

    //Удаляем лайк фильму от конкретного пользователя, проверив, существуют ли они, и не был ли удален этот лайк ранее
    public Film removeLike(Integer id, Integer userId) {
        filmValidateService.checkGetFilmValidation(log, filmStorage.getFilms(), id);
        userValidateService.checkGetUserValidate(log, userStorage.getUsers(), id);
        filmValidateService.checkRemoveLikeValidate(filmStorage, id, userId);
        filmStorage.getFilms().get(id).getLikes().remove(userId);
        return filmStorage.getFilms().get(id);
    }

    @Override
    public int compare(Film f0, Film f1) {
        return Integer.compare(f1.getLikes().size(), f0.getLikes().size());
    }

}
