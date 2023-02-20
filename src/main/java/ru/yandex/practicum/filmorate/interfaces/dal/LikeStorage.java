package ru.yandex.practicum.filmorate.interfaces.dal;

import java.util.HashSet;

public interface LikeStorage {
    void addLike(long filmId, long userId);

    void removeLike(long filmId, long userId);

    HashSet<Long> getListOfLikes(long filmId);

    HashSet<Long> getTheBestFilms(int count);

}