package model;


import java.util.Objects;

import lombok.Builder;
import lombok.ToString;

@ToString
@Builder
public class User {
    private String dniUser;
    private String username;
    private String password;
    private String tipo;

    public User(String dniUser, String username, String password,String tipo) {
        this.dniUser = dniUser;
        this.username = username;
        this.password = password;
        this.tipo = tipo;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.tipo = "normal";
    }

    public User() {
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDniUser() {
        return dniUser;
    }

    public void setDniUser(String dniUser) {
        this.dniUser = dniUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dniUser);
    }

}
