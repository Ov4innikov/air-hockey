package ru.airhockey.web.auth;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.airhockey.web.auth.AppUser;
import ru.airhockey.web.auth.UserMapper;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
@Transactional
public class AppUserDao extends JdbcDaoSupport {

    @Autowired
    public AppUserDao(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public AppUser findUserAccount(String userName)  throws DataAccessException {
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

    public void registerUserAccount(AppUser user) throws DataAccessException {
        String sql = "INSERT INTO public.\"USER\"(name,login,password,city,description) " +
                "VALUES (?,?,?,?,?);";

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String codePass = passwordEncoder.encode(user.getPassword());
        Object[] params = new Object[] {user.getName(),user.getLogin(),codePass,
                user.getCity(),user.getDescription()};

        this.getJdbcTemplate().update(sql,params);
    }
}
