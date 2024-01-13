package pt.ipleiria.estg.dei.lusitaniatravel.modelos;

import java.util.List;

public class User {
    private int id;
    private String username, password, repeatPassword, email;
    private Profile profile;

    public User(int id, String username, String password, String repeatPassword, String email, Profile profile) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.email = email;
        this.profile = profile;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + '\'' + ", password='" + password + '\'' + '}';
    }
}
