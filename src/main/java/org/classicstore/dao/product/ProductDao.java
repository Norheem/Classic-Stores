package org.classicstore.dao.product;

import org.classicstore.model.Admin;
import org.classicstore.model.Product;
import org.classicstore.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao{


    public boolean addProduct(Product product, Admin admin) {
        if (admin == null) {
            System.out.println("Access denied. Only admins can add products.");
            return false;
        }
        String query = "INSERT INTO products (productName, productPrice, productDescription, productCategory, productImage) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, product.getProductName());
            statement.setDouble(2, product.getProductPrice());
            statement.setString(3, product.getProductDescription());
            statement.setString(4, product.getProductCategory());
            statement.setBytes(5, product.getProductImage());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Product added successfully: " + product.getProductName());
                return true;
            } else {
                System.out.println("Failed to add product. No rows affected.");
                return false;
            }

        } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Error inserting product: " + e.getMessage());
        return false;
    }
}

    public Product getProductById(int productId) {
        String query = "SELECT * FROM products WHERE productID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Product product = new Product();
                product.setProductID(resultSet.getInt("productID"));
                product.setProductName(resultSet.getString("productName"));
                product.setProductPrice(resultSet.getDouble("productPrice"));
                product.setProductDescription(resultSet.getString("productDescription"));
                product.setProductCategory(resultSet.getString("productCategory"));
                product.setProductImage(resultSet.getBytes("productImage"));
                return product;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void updateProduct(Product product, Admin admin) {

        if (admin == null) {
            System.out.println("Access denied. Only admins can update products.");
            return;
        }

        String query = "UPDATE products SET productName = ?, productPrice = ?, productDescription = ?, productCategory = ?, productImage = ? WHERE productID = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, product.getProductName());
            statement.setString(2, String.valueOf(product.getProductPrice()));
            statement.setString(3, product.getProductDescription());
            statement.setString(4, product.getProductCategory());
            statement.setBytes(5, product.getProductImage());
            statement.setString(6, String.valueOf(product.getProductID()));

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0){
                System.out.println("Product updated successfully");
            }else {
                System.out.println("Product not updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update product\n" + e.getMessage());
        }
    }


    public void deleteAllProducts(Admin admin) {
        if (admin == null) {
            System.out.println("Access denied. Only admins can delete all products.");
            return;
        }

        String query = "DELETE FROM products";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.executeUpdate();
            System.out.println("All products deleted successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to delete all products \n" + e.getMessage());
        }

    }


    public void deleteProduct(Product product, Admin admin) {
        if (admin == null) {
            throw new IllegalArgumentException("Access denied. Only admins can delete products.");
        }

        String query = "DELETE FROM products WHERE productID = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, String.valueOf(product.getProductID()));
            int rowsDeleted = statement.executeUpdate();


            if (rowsDeleted > 0) {
                System.out.println("Product deleted successfully.");
            } else {
                System.out.println("No product found with the provided ID.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to delete product: " + e.getMessage());
            throw new RuntimeException("Error deleting product", e);
        }
    }


    public List<Product> displayAllProducts() {

        String query = "SELECT * FROM products";
        List<Product> products = new ArrayList<>();


        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("Fetching products from database...");


            while (resultSet.next()) {
                Product product = new Product();
                product.setProductID(resultSet.getInt("productID"));
                product.setProductName(resultSet.getString("productName"));
                product.setProductPrice(resultSet.getDouble("productPrice"));
                product.setProductCategory(resultSet.getString("productCategory"));
                product.setProductDescription(resultSet.getString("productDescription"));
                product.setProductImage(resultSet.getBytes("productImage"));

                System.out.println("Fetched product: " + product.getProductName());
                products.add(product);

                System.out.println("Products retrieved: ");
                for (Product hello : products) {
                    System.out.println(product.getProductName());
                    System.out.println(hello.getProductCategory());
                }

                System.out.println("Display all products");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to retrieve products\n" + e.getMessage());
        }
        return products;
    }

}
