package ru.airhockey.statistics.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.airhockey.statistics.entity.UserStatistics;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapper для статистики игрока
 * @author folkland
 */
public class UserStatisticsMapper implements RowMapper<UserStatistics> {

    @Override
    public UserStatistics mapRow(ResultSet resultSet, int i) throws SQLException {
        UserStatistics statistics = new UserStatistics();
        statistics.setId(resultSet.getInt("id"));
        statistics.setIdUser(resultSet.getInt("id_user"));
        statistics.setMatchCount(resultSet.getInt("match_count"));
        statistics.setWinCount(resultSet.getInt("win_count"));
        statistics.setBotMatchCount(resultSet.getInt("bot_match_count"));
        statistics.setBotWinCount(resultSet.getInt("bot_win_count"));
        statistics.setScoredPuck(resultSet.getInt("scored_puck"));
        statistics.setMissedPuck(resultSet.getInt("missed_puck"));
        statistics.setElo(resultSet.getInt("elo"));
        return statistics;
    }
}
