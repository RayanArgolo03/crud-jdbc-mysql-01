package dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public final class UserDTO {
    private final Long id;
    private final String username;
    private final String password;
}
