package ru.airhockey.web.auth;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<AppUser> {
    @Override
    public AppUser mapRow(ResultSet rs,int rowNum) throws SQLException{
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String login = rs.getString("login");
        String password = rs.getString("password");
        String city = rs.getString("city");
        String decriptinon = rs.getString("description");

        return new AppUser(id, name,login,password,city,decriptinon);


    }
}
