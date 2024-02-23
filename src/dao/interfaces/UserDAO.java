package dao.interfaces;

import domain.user.User;
import dto.user.UserDTO;
import dto.user.UserUsernameDTO;

import java.util.Optional;

public interface UserDAO extends EntityDAO<User> {

    void save(User user);

    void delete(Long id, String username);

    Optional<UserUsernameDTO> findByName(String username);

    Optional<UserDTO> findUser(String username, String password);

}
