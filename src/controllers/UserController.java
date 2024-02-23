package controllers;

import domain.user.User;
import dto.user.UserDTO;
import dto.user.UserUsernameDTO;
import exceptions.UserException;
import lombok.extern.log4j.Log4j2;
import services.UserService;

import java.util.Optional;

@Log4j2
public final class UserController {

    private final UserService userService;

    public UserController() {
        userService = new UserService();
    }

    public User create() {

        log.info("Tryning to create a new user.. \n");

        String username = userService.receiveUsername();

        while (this.find(username).isPresent()) {
            System.out.println("Error! Username alredy in use!");
            username = userService.receiveUsername();
        }

        String password = userService.receivePassword();

        User user = new User(username, password);
        userService.saveUser(user);

        return user;
    }

    public Optional<UserUsernameDTO> find(String username) {
        return userService.findUser(username);
    }

    public UserDTO find() {

        log.info("Tryning find a user.. \n");

        if (!userService.hasUsersInDatabase()) throw new UserException("There are no users in the database!");

        String username = userService.receiveUsername();
        String password = userService.receivePassword();

        return userService.findUser(username, password)
                .orElseThrow(() -> new UserException("User not found!"));
    }

    public void delete() {
        UserDTO userDTO = this.find();
        userService.deleteUser(userDTO.getId(), userDTO.getUsername());
    }

}
