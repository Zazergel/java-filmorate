package ru.yandex.practicum.filmorate.interfaces.dal;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface FilmGenresStorage {
    void addGenres(List<Genre> genres, long filmId);

    void deleteGenres(long filmId);

    List<Integer> getListOfGenres(long id);

}