package pt.ipleiria.estg.dei.lusitaniatravel.modelos;

public class Profile {
    private int id;
    private String name, mobile, street, locale, postalCode, role, usernameUser;

    public Profile(int id, String name, String mobile, String street, String locale, String postalCode, String role, String usernameUser) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.street = street;
        this.locale = locale;
        this.postalCode = postalCode;
        this.role = role;
        this.usernameUser = usernameUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsernameUser() {
        return usernameUser;
    }

    public void setUsernameUser(String usernameUser) {
        this.usernameUser = usernameUser;
    }
}
