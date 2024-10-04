package praktikum.courier;

import org.apache.commons.lang3.RandomStringUtils;

public class Courier {
    private final String login;
    private final String password;
    private final String firstName;
    static String fix = "courier";

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public static Courier random() {
        return new Courier(fix + RandomStringUtils.randomAlphanumeric(5, 15), fix, fix);
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public String getFirstName() {
        return firstName;
    }
}