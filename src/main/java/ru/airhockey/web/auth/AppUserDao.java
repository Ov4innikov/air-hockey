package ru.airhockey.web.auth;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.airhockey.web.auth.AppUser;
import ru.airhockey.web.auth.UserMapper;

@Repository
@Transactional
public class AppUserDao extends JdbcDaoSupport {

    @Autowired
    public AppUserDao(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public AppUser findUserAccount(String userName){
        String sql = "SELECT name, login, password, city, description from public.\"USER\" where name=?";
        Object[] params = new Object[] {userName};
        UserMapper mapper = new UserMapper();
        try{
            AppUser user = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return user;
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public void registerUserAccount(String userName,String login,String password,String city,String description){
        String sql = "INSERT INTO public.\"USER\"(name,login,password,city,description) " +
                "VALUES (?,?,?,?,?);";
        Object[] params = new Object[] {userName,login,password,city,description};
        UserMapper mapper = new UserMapper();
        try{
            this.getJdbcTemplate().query(sql,params,mapper);
        } catch (EmptyResultDataAccessException e){
            e.printStackTrace();
        }
    }
}
