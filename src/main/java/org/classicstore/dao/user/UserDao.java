package org.classicstore.dao.user;

import jakarta.servlet.http.HttpSession;
import org.classicstore.model.User;
import org.classicstore.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    public String userRegister(User user) {
        String query = "INSERT INTO users (firstName, lastName, email, password) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return "User added successfully.";
            } else {
                return "Failed to add user.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Failed to add user \n" + e.getMessage();
        }
    }


    public String userLogin(String email, String password) {

        String query = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
               if (resultSet.next()) {
                   return "User logged in successfully";
               } else {
                   return "Invalid username or password.";
               }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Database error \n" + e.getMessage();
        }
    }


    public void userLogout(HttpSession session) {
        if (session != null) {
            session.removeAttribute("email");
            session.invalidate();
        }
    }

}