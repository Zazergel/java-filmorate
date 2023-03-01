package ru.yandex.practicum.filmorate.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.interfaces.dal.MpaStorage;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class MpaService {
    private final MpaStorage mpaStorage;

    public Collection<Mpa> getMpa() {
        return mpaStorage.getMpa();
    }

    public Mpa getMpaById(int id) {
        return mpaStorage.getMpaById(id);
    }
}