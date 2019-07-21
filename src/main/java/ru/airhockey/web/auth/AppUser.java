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
    private String name;
    private String login;
    private String password;
    private String city;
    private String description;

    public AppUser(String name, String login, String password, String city, String description) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.city = city;
        this.description = description;
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

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
