package ru.airhockey.statistics.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.airhockey.statistics.entity.UserResult;
import ru.airhockey.statistics.entity.UserStatistics;

import javax.sql.DataSource;

/**
 * Реализация даошки
 * @author folkland
 */
@Repository
@Transactional
public class UserStatisticsTemplate extends JdbcDaoSupport implements UserStatisticsDAO {

    @Autowired
    public UserStatisticsTemplate(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    @Override
    public UserStatistics getStatisticsByUserId(int idUser) {
        String sql = "select * from public.USER_STATISTICS where id_user = ?";
        Object[] params = new Object[] {idUser};
        RowMapper<UserStatistics> mapper = new UserStatisticsMapper();
        UserStatistics statistics = new UserStatistics();
        try {
            this.getJdbcTemplate().queryForObject(sql, params, mapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statistics;
    }

    @Override
    public void insertStatistics(int idUser) {
        String sql = "insert into public.USER_STATISTICS(id_user, match_count, win_count, bot_match_count," +
                "bot_win_count, scored_puck, missed_puck, elo) values(?, 0, 0, 0, 0, 0, 0, 0)";
        this.getJdbcTemplate().update(sql, idUser);
    }

    @Override
    public void updateStatistics(int idUser, UserResult result, int scored, int missed, boolean isBot) {
        if (idUser == -1) return;

        UserStatistics statistics;
        try {
            statistics = getStatisticsByUserId(idUser);
        } catch (Exception e) {
            e.printStackTrace();
            insertStatistics(idUser);
            statistics = getStatisticsByUserId(idUser);
        }
        statistics.setScoredPuck(statistics.getScoredPuck() + scored);
        statistics.setMissedPuck(statistics.getMissedPuck() + missed);
        if (isBot) {
            statistics.setBotMatchCount(statistics.getBotMatchCount() + 1);
            if (result == UserResult.WIN) statistics.setBotWinCount(statistics.getBotWinCount() + 1);
        }
        else {
            statistics.setMatchCount(statistics.getMatchCount() + 1);
            if (result == UserResult.WIN) statistics.setWinCount(statistics.getWinCount() + 1);
        }
        String sql = "update public.USER_STATISTICS set match_count = ?, win_count = ?, bot_match_count = ?," +
                "bot_win_count = ?, scored_puck = ?, missed_puck = ?, elo = ? where id_user = ?";
        this.getJdbcTemplate().update(sql, statistics.getMatchCount(), statistics.getWinCount(), statistics.getBotMatchCount(),
                statistics.getBotWinCount(), statistics.getScoredPuck(), statistics.getMissedPuck(), statistics.getElo(), idUser);
    }
}
