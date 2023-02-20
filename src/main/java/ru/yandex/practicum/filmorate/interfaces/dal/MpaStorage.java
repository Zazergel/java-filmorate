package ru.yandex.practicum.filmorate.interfaces.dal;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

public interface MpaStorage {
    Collection<Mpa> getMpa();

    Mpa getMpaById(int mpaId);
}