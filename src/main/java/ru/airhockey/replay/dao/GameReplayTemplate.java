package ru.airhockey.replay.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.airhockey.replay.entity.GameReplay;

import javax.sql.DataSource;

/**
 * @author folkland
 */
@Repository
@Transactional
public class GameReplayTemplate extends JdbcDaoSupport implements GameReplayDAO {

    @Autowired
    public GameReplayTemplate(DataSource dataSource) { this.setDataSource(dataSource); }

    @Override
    public void insertGame(String gameId, String gameText) {
        String sql = "insert into public.GAME_REPLAY(game_id, game_text) values(?,?)";
        this.getJdbcTemplate().update(sql, gameId, gameText);
    }

    @Override
    public GameReplay getGameById(String gameId) {
        String sql = "select * from public.GAME_REPLAY where game_id = ?";
        Object[] params = new Object[] {gameId};
        GameReplayMapper mapper = new GameReplayMapper();
        GameReplay replay = this.getJdbcTemplate().queryForObject(sql, params, mapper);
        return replay;
    }

    @Override
    public void clearTable() {
        String sql = "truncate public.GAME_REPLAY";
        this.getJdbcTemplate().execute(sql);
    }
}
