package ru.yandex.practicum.filmorate.interfaces;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorageIm {

    Film getFilm(Long id);

    List<Film> getAllFilms();

    Film addNewFilm(Film film);

    Film updateFilm(Film film);

}
