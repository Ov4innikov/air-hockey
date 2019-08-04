package ru.airhockey.statistics.dao;

import ru.airhockey.statistics.entity.UserResult;
import ru.airhockey.statistics.entity.UserStatistics;

/**
 * Интерфейс дао для взаимодействию с сущностью статистики
 * @author folkland
 */
public interface UserStatisticsDAO {

    UserStatistics getStatisticsByUserId(int idUser);
    void insertStatistics(int idUser);
    void updateStatistics(int idUser, UserResult result, int scored, int missed, boolean isBot);
}
