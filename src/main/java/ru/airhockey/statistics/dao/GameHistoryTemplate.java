package ru.airhockey.statistics.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.airhockey.playingarea.model.PlayerPosition;
import ru.airhockey.statistics.entity.GameHistory;

import javax.sql.DataSource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author folkland
 */
@Repository
@Transactional
public class GameHistoryTemplate extends JdbcDaoSupport implements GameHistoryDAO {

    @Autowired
    public GameHistoryTemplate(DataSource dataSource) { this.setDataSource(dataSource); }

    @Override
    public void insertGame(String idGame, int idUser, int opponent, boolean isWin, PlayerPosition position) {
        String sql = "insert into public.GAME_HISTORY(id_game, id_user, opponent, is_win, position, game_date) values(?,?,?,?,?,?)";
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        this.getJdbcTemplate().update(sql, idGame, idUser, opponent, isWin, position.toString(), format.format(new Date()));
    }

    @Override
    public List<GameHistory> getGamesByIdUser(int idUser) {
        String sql = "select * from public.GAME_HISTORY where id_user = ?";
        Object[] params = new Object[] {idUser};
        GameHistoryMapper mapper = new GameHistoryMapper();
        List<GameHistory> historyList = this.getJdbcTemplate().query(sql, params, mapper);
        return historyList;
    }
}
