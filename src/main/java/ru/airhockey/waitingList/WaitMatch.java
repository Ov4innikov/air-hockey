package ru.airhockey.waitingList;


import java.util.HashMap;
import java.util.Map;

/**
 * временный сборщик еще не начатых игр
 *
 * @author sibagatullin
 */
public class WaitMatch {

    private Map<Integer, MatchedUser> waitMatch;

    public WaitMatch() {
        waitMatch = new HashMap<>();
    }

    public void addWaitMatch(int user, MatchedUser matchedUser) {
        waitMatch.put(user, matchedUser);
    }

    public void deleteWaitMatch(int user) {
        waitMatch.remove(user);
    }

    public MatchedUser findWaitMatch(int user) {
        return waitMatch.get(user);
    }

    public void deleteByMatchUser(MatchedUser matchedUser) {
        waitMatch.values().removeIf(val -> matchedUser.equals(val));
    }
}
