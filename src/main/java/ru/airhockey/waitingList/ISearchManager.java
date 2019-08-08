package ru.airhockey.waitingList;

/**
 *
 * @author sibagatullin
 */
public interface ISearchManager {

    void addQueue(int user);

    void removeQueue(int user);

    MatchedUser matchUserAndStart(int key);
    }
