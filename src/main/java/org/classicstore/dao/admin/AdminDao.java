package org.classicstore.dao.admin;

import jakarta.servlet.http.HttpSession;
import org.classicstore.model.Admin;
import org.classicstore.utils.DBConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDao {

    public String adminLogin(String adminEmail, String adminPassword) {
        String query = "SELECT * FROM admin WHERE adminEmail = ? AND adminPassword = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, adminEmail);
            statement.setString(2, adminPassword);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return "Login Successful";
                } else {
                    return "Invalid email or password";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Database error: " + e.getMessage();
        }
    }


//    public String adminLogin(String adminEmail, String adminPassword) {
//        String query = "SELECT * FROM admin WHERE adminEmail = ? AND adminPassword = ?";
//
//        try (Connection connection = DBConnection.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//
//            statement.setString(1, adminEmail);
//            statement.setString(2, adminPassword);
//
//            try (ResultSet resultSet = statement.executeQuery()) {
//                return resultSet.next() ? "Login Successful" : "Invalid email or password";
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return "Database error \n" + e.getMessage();
//        }
//    }



//    public Admin adminLogin(String email, String password) {
//        Admin admin = null;
//        String query = "SELECT * FROM admins WHERE email = ? AND password = ?";
//
//        try (Connection connection = DBConnection.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//
//            statement.setString(1, email);
//            statement.setString(2, password);
//
//            System.out.println("Executing query: " + statement);
//
//            try (ResultSet resultSet = statement.executeQuery()) {
//                if (resultSet.next()) {
//                    admin = new Admin();
//                    admin.setAdminEmail(resultSet.getString("adminEmail"));
//                    admin.setAdminPassword(resultSet.getString("adminPassword"));
//                    // Add other fields as needed
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return admin;
//    }



    public String adminRegister(Admin admin) {
        String query = "INSERT INTO admin (adminFirstName, adminLastName, adminEmail, adminPassword) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, admin.getAdminFirstName());
            statement.setString(2, admin.getAdminLastName());
            statement.setString(3, admin.getAdminEmail());
            statement.setString(4, admin.getAdminPassword());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return "Admin registered successfully.";
            } else {
                return "Failed to register admin.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        return  "Failed to register admin \n" + e.getMessage();
        }
    }


    public void adminUpdate(Admin admin) {
        String query = "UPDATE admin SET adminFirstName = ?, adminLastName = ?, adminEmail = ?, adminPassword = ? WHERE adminID = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, admin.getAdminFirstName());
            statement.setString(2, admin.getAdminLastName());
            statement.setString(3, admin.getAdminEmail());
            statement.setString(4, admin.getAdminPassword());
            statement.setString(5, String.valueOf(admin.getAdminID()));

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Admin updated successfully");
            } else {
                System.out.println("Admin not found and no change made");
            }
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Failed to update admin\n" + e.getMessage());
        }
    }


    public void adminDelete(Admin admin) {
        String query = "DELETE FROM admin WHERE adminID = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, String.valueOf(admin.getAdminID()));

            int updateAdmin = statement.executeUpdate();
            if (updateAdmin > 0) {
                System.out.println("Admin deleted successfully");
            } else {
                System.out.println("Admin not found and no change made");
            }
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Failed to delete admin account\n" + e.getMessage());
        }
    }


    public void adminLogout(HttpSession session) {
        if (session != null) {
            session.removeAttribute("adminEmail");
            session.invalidate();
        }
    }
}
