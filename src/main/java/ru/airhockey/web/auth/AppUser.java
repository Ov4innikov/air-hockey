package ru.airhockey.web.auth;

/**
 *        <createTable tableName="USER">
 *             <column name="id" type="serial">
 *                 <constraints nullable="false"/>
 *             </column>
 *             <column name="name" type="VARCHAR(255)">
 *                 <constraints nullable="false"/>
 *             </column>
 *             <column name="login" type="varchar(255)"/>
 *             <column name="password" type="varchar(255)"/>
 *             <column name="city" type="varchar(255)"/>
 *             <column name="description" type="varchar(255)"/>
 */

public class AppUser {
    private int id;
    private String name;
    private String login;
    private String password;
    private String city;
    private String description;
    private String passwordConfirm;

    public AppUser(int id, String name, String login, String password, String city, String description) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.city = city;
        this.description = description;
    }

    public AppUser() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", city='" + city + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
