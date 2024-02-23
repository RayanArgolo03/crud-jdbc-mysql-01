package services;

import dao.impl.UserDAOImpl;
import dao.interfaces.UserDAO;
import domain.user.User;
import dto.user.UserDTO;
import dto.user.UserUsernameDTO;
import enums.DefaultMessage;
import exceptions.UserException;

import java.util.Optional;

public final class UserService {

    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAOImpl();
    }

    public boolean hasUsersInDatabase() {
        return userDAO.hasRegisters();
    }

    public String receiveUsername() {

        System.out.printf("%s %s: (off whitespaces, more than 3 characters) \n",
                DefaultMessage.ENTER_WITH.getValue(), "username");
        String username = ReaderService.readString();

        if (!validUsername(username)) {
            throw new UserException("Invalid username! Your username has whitespaces or less than 4 characters!");
        }

        return username;
    }


    public String receivePassword() {

        System.out.printf("%s %s: (off whitespaces, with at least 1 special character) \n",
                DefaultMessage.ENTER_WITH.getValue(), "password");
        String password = ReaderService.readString();

        if (!validPassword(password)) {
            throw new UserException("Invalid username! Your username has whitespaces or less than 1 special characters!");
        }

        return password;
    }

    public void saveUser(User user) {

        if (user == null) {
            throw new UserException("User can´t be null!");
        }

        userDAO.save(user);
    }

    public void deleteUser(Long id, String username) {

        if (id == null) {
            throw new UserException("ID can´t be null!");
        }

        if (username == null) {
            throw new UserException("Username can´t be null!");
        }

        userDAO.delete(id, username);
    }

    public Optional<UserUsernameDTO> findUser(String username) {

        if (username == null) {
            throw new UserException("Username can´t be null!");
        }

        return userDAO.findByName(username);
    }

    public Optional<UserDTO> findUser(String username, String password) {

        if (username == null) {
            throw new UserException("Username can´t be null!");
        }
        if (password == null) {
            throw new UserException("Password can´t be null!");
        }

        return userDAO.findUser(username, password);
    }

    private boolean validPassword(String password) {
        return !hasWhitespaces(password) && password.matches(".*[!@#$%^&*()\\-+=_{}|:;',.?/\\\\].*");
    }

    private boolean validId(Long id) {
        return id > 0;
    }

    private boolean validUsername(String username) {
        return !hasWhitespaces(username) && username.length() > 3;
    }

    private boolean hasWhitespaces(String str) {
        return str.contains(" ");
    }
}
