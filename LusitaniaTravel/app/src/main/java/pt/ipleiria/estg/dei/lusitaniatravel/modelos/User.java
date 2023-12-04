package pt.ipleiria.estg.dei.lusitaniatravel.modelos;

public class User {
    private int id;
    private String username, password, repeatPassword, email;

    public User(int id, String username, String password, String repeatPassword, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.email = email;
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
}
