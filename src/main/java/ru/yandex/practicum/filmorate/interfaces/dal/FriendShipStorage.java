package ru.yandex.practicum.filmorate.interfaces.dal;

import java.util.HashSet;
import java.util.List;

public interface FriendShipStorage {
    boolean addAsFriend(long userId, long friendId);

    boolean removeFromFriends(long userId, long friendId);

    HashSet<Long> getListOfFriends(long userId);

    List<Long> getAListOfMutualFriends(long userId, long otherId);

}
