package ru.airhockey.waitingList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.airhockey.web.ws.sender.ISender;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.locks.LockSupport;

/**
 * @author sibagatullin
 */
@Component
public class SearchPlayerManager implements ISearchManager {

    @Autowired
    ISender sender;

    private List<Integer> waitListMap;

    private WaitMatch waitMatch;

    public SearchPlayerManager() {
        waitListMap = new ArrayList<Integer>() {
        };
        waitMatch = new WaitMatch();
    }

    @Override
    public void addQueue(int userID) {
        waitListMap.add(userID);
    }

    @Override
    public void removeQueue(int userID) {
        waitListMap.remove(waitListMap.indexOf(userID));
    }

    @Override
    public MatchedUser matchUserAndStart(int key) {

        MatchedUser matchedUser = null;
        while (waitListMap.size() < 2) {
            MatchedUser matchedUser2 = waitMatch.findWaitMatch(key);
            if (matchedUser2 != null) {
                waitMatch.deleteByMatchUser(matchedUser2);
                return matchedUser2;
            }
            LockSupport.parkNanos(300000000);
        }
        int size = waitListMap.size();
        if (size >= 2) {
            Random random = new Random();
            int user1 = waitListMap.get(random.nextInt(size));
            removeQueue(user1);
            int user2 = waitListMap.get(random.nextInt(waitListMap.size()));
            removeQueue(user2);
            String gameID;
            gameID = String.valueOf(user1) + "_" + String.valueOf(user2) + "_" + Instant.now().toString();

            matchedUser = new MatchedUser(user1, user2, gameID);
//            if (key == user1) {
            waitMatch.addWaitMatch(user2, matchedUser);
//            } else {
            waitMatch.addWaitMatch(user1, matchedUser);
//            }
        }
        ;

        return matchedUser;
    }
}
