package domain.user;

import lombok.Getter;
import lombok.Setter;


@Getter
public final class User {

    @Setter
    private Long id;
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(Long id) {
        this.id = id;
    }

}
