package dao.impl;

import dao.interfaces.UserDAO;
import database.DbConnection;
import domain.user.User;
import dto.user.UserDTO;
import dto.user.UserUsernameDTO;
import exceptions.DbConnectionException;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.Optional;

@Log4j2
public final class UserDAOImpl implements UserDAO {

    @Override
    public boolean hasRegisters() {

        log.info("Checking for records..");

        try (Connection c = DbConnection.getConnection();
             ResultSet rs = c.createStatement().executeQuery("SELECT u.id FROM users AS u LIMIT 1")) {

            if (rs.next()) return true;

        } catch (SQLException e) {
            throw new DbConnectionException(e.getMessage());
        }

        return false;
    }

    @Override
    public void save(User user) {

        log.info("Saving {} in the database.. \n", user.getUsername());

        try (Connection c = DbConnection.getConnection();
             PreparedStatement ps = this.createQueryForSaveUser(c, user);
             ResultSet rs = this.executeSaveUser(ps)) {

            c.setAutoCommit(false);

            if (!rs.next()) throw new DbConnectionException("Addition not completed!");

            Long id = rs.getLong(1);
            user.setId(id);

            c.commit();

        } catch (SQLException e) {
            throw new DbConnectionException(e.getMessage());
        }
    }

    @Override
    public void delete(Long id, String username) {

        log.info("Deleting user {}", username);

        try (Connection c = DbConnection.getConnection();
             PreparedStatement ps = this.createQueryForDeleteUser(c, id, username)) {

            c.setAutoCommit(false);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected < 1) throw new DbConnectionException("Exclusion not completed!");

            c.commit();

        } catch (SQLException e) {
            throw new DbConnectionException(e.getMessage());
        }
    }

    @Override
    public Optional<UserUsernameDTO> findByName(String username) {

        log.info("Finding {} in the database.. \n", username);

        try (Connection c = DbConnection.getConnection();
             PreparedStatement ps = this.createQueryForFindUser(c, username);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String foundUsername = rs.getString("username");
                return Optional.of(new UserUsernameDTO(foundUsername));
            }

        } catch (SQLException e) {
            throw new DbConnectionException(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public Optional<UserDTO> findUser(String username, String password) {

        log.info("Finding {} in the database.. \n", username);

        try (Connection c = DbConnection.getConnection();
             PreparedStatement ps = this.createQueryForFindUser(c, username, password);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                Long id = rs.getLong("id");
                String foundUsername = rs.getString("username");
                String foundPassword = rs.getString("password");
                return Optional.of(new UserDTO(id, foundUsername, foundPassword));
            }

        } catch (SQLException e) {
            throw new DbConnectionException(e.getMessage());
        }

        return Optional.empty();
    }

    private ResultSet executeSaveUser(PreparedStatement ps)
            throws SQLException {

        ps.execute();
        return ps.getGeneratedKeys();
    }

    private PreparedStatement createQueryForSaveUser(Connection c, User user)
            throws SQLException {

        PreparedStatement ps = c.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, user.getUsername());
        ps.setString(2, user.getPassword());
        return ps;
    }

    private PreparedStatement createQueryForDeleteUser(Connection c, Long id, String username)
            throws SQLException {

        PreparedStatement ps = c.prepareStatement("DELETE FROM users AS u WHERE u.id = ? AND u.username = ?");
        ps.setLong(1, id);
        ps.setString(2, username);
        return ps;
    }

    private PreparedStatement createQueryForFindUser(Connection c, String username)
            throws SQLException {

        PreparedStatement ps = c.prepareStatement("SELECT u.username FROM users AS u WHERE u.username = ?");
        ps.setString(1, username);
        return ps;
    }

    private PreparedStatement createQueryForFindUser(Connection c, String username, String password)
            throws SQLException {

        PreparedStatement ps = c.prepareStatement("SELECT * FROM users AS u WHERE u.username = ? AND u.password = ?");

        ps.setString(1, username);
        ps.setString(2, password);
        return ps;
    }


}
