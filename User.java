package asf2;

import java.io.Serializable;

public class User implements Serializable {

    private String name;
    private String surname;
    private String email;
    private String username;
    private String password;
    private byte[] bSalt;
    private static final long serialVersionUID = 1L;

    public User(String name, String surname, String email, String username, String password, byte[] bSalt) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.bSalt = bSalt;
    }

    protected String getName() {
        return name;
    }

    protected byte[] getSalt() {
        return bSalt;
    }

    protected String getSurname() {
        return surname;
    }

    protected String getEmail() {
        return email;
    }

    protected String getUsername() {
        return username;
    }

    protected String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{ " + "name=" + name + ", surname=" + surname + ", email=" + email + ", username=" + username + ", password=" + password + ", bSalt=" + bSalt + '}';
    }

}
